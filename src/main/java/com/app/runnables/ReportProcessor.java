package com.app.runnables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Callable;
import com.app.beans.BankAccount;
import com.app.beans.BankAccountTransaction;
import com.app.dao.BankAccountDAO;

/**
 * @author Anudeep
 *
 */
public class ReportProcessor implements Callable<Boolean> {

	// Single task of ReportProcessor for each Account
	private BankAccount account;
	private BankAccountDAO dao;
	
	public ReportProcessor(BankAccount account, BankAccountDAO dao) {
		this.account = account;
		this.dao = dao;
	}
	
	@Override
	public Boolean call() throws Exception {
		System.out.println("URL checker thread name: "+Thread.currentThread().getName());
		Boolean reportGenerated  = false;
		List<BankAccountTransaction> transactions = dao.getTransactionsForAccount(account);
		File file = new File("E:/ApplicationDev/JavaEE/output/"+account.getAccountNumber()+"_txn_report.txt");

		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (BankAccountTransaction transaction : transactions) {
				writer.write("Account Number: "+transaction.getAccountNumber()+"\t");
				writer.write("Transaction Type: "+transaction.getTransactionType()+"\t");
				writer.write("Transaction Id: "+transaction.getTransactionId()+"\t");
				writer.write("Amount: "+transaction.getAmount()+"\t");
				writer.write("Transaction Date: "+transaction.getTransactionDate()+"\t");
				writer.newLine();
			}
			writer.flush();	
		}
		reportGenerated = true;
		return reportGenerated;
	}
}
