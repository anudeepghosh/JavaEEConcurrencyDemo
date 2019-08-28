/**
 * 
 */
package com.app.runnables;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.app.ejbbeans.TransactionBean;

/**
 * @author Anudeep
 *
 */
public class TransactionProcessor implements Runnable {
	
	private UserTransaction userTransaction;
	private TransactionBean bean;
	
	public TransactionProcessor() {
		
		InitialContext context;
		try {
			context = new InitialContext();
			userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
			bean = (TransactionBean)context.lookup("java:module/tx-bean");
		} catch (NamingException e) {
			Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}
	
	@Override
	public void run() {
		try {
			userTransaction.begin();
			bean.saveBankAccountTransaction();
			bean.saveBankAccountTransactionLog();
			userTransaction.commit();
		} catch (Exception e) {
			System.out.println("ROLLING BACK. Cause: "+e.getMessage());
			try {
				userTransaction.rollback();
			} catch (IllegalStateException e1) {
				Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, e1);
			} catch (SecurityException e1) {
				Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, e1);
			} catch (SystemException e1) {
				Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, e1);
			}
		}
	}
	
}
