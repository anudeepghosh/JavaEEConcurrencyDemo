package com.app.ejbbeans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 * Session Bean implementation class TransactionBean
 */
@Stateless(name="tx-bean")
public class TransactionBean {
	
	@Resource(lookup="jdbc/source1")
	private DataSource source1;
	
	@Resource(lookup="jdbc/source2")
	private DataSource source2;
	
	public void saveBankAccountTransaction() throws SQLException {
		Connection connection = source1.getConnection();
		Statement statement = connection.createStatement();
		// Execution - Success
		/*statement.executeUpdate("INSERT INTO bank_account_transaction VALUES(23, 1101, 'debit', 400,'"+
		new Date(System.currentTimeMillis())+"')");*/
		
		statement.executeUpdate("INSERT INTO bank_account_transaction VALUES(24, 1102, 'creit', 3000,'"+
				new Date(System.currentTimeMillis())+"')");
		
	}
	
	public void saveBankAccountTransactionLog() throws SQLException {
		Connection connection = source2.getConnection();
		Statement statement = connection.createStatement();
		// Execution - Success
		/*statement.executeUpdate("INSERT INTO bank_account_transaction_log VALUES(4, 23, 'system','"+
		new Date(System.currentTimeMillis())+"')");*/
		
		statement.executeUpdate("INSERT INTO bank_account_transaction_log VALUES(5, 26, 'system','"+
				new Date(System.currentTimeMillis())+"')");
	}

}
