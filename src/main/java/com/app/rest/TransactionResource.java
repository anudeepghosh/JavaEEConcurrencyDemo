package com.app.rest;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.app.runnables.TransactionProcessor;

/**
 * @author Anudeep
 *
 */
@Path("transactions")
public class TransactionResource {
	
	@Resource(lookup="java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService service;
	
	@POST
	public String executeTransactions() {
		
		service.submit(new TransactionProcessor());
		return "Transaction submitted, verify log...";
	}
}
