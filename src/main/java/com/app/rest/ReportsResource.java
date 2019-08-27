package com.app.rest;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.app.beans.BankAccount;
import com.app.dao.BankAccountDAO;
import com.app.runnables.ReportProcessor;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Anudeep
 *
 */
@Path("reports")
public class ReportsResource {
	
	private BankAccountDAO dao;
	
	// MES injection through @Resource Annotation
	@Resource(lookup="java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService service;
	
	public ReportsResource() {
		
		// MES injection through  JNDI lookup
		/*try {
			InitialContext context = new InitialContext();
			service = (ManagedExecutorService) context.lookup("java:comp/DefaultManagedExecutorService");
		} catch (NamingException e) {
			Logger.getLogger(ReportsResource.class.getName()).log(Level.SEVERE, null, e);
		}*/
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testthreadapi?autoReconnect=true&useSSL=false");
			dataSource.setDriverClass("com.mysql.jdbc.Driver"); //com.mysql.cj.jdbc.Driver
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dao = new BankAccountDAO(dataSource);
		} catch (PropertyVetoException e) {
			Logger.getLogger(ReportsResource.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	@GET
	public String generateReports() {
		List<BankAccount> accounts = dao.getAllBankAccounts();
		
		for (BankAccount account : accounts) {
			try {
				Future<Boolean> future = service.submit(new ReportProcessor(account, dao));
				System.out.println("Report generated? "+future.get());
			} catch (InterruptedException e) {
				Logger.getLogger(ReportsResource.class.getName()).log(Level.SEVERE, null, e);
			} catch (ExecutionException e) {
				Logger.getLogger(ReportsResource.class.getName()).log(Level.SEVERE, null, e);
			}
		}
		
		return "Report generation tasks submitted...";
	}
}
