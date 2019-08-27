package com.app.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;
import com.app.beans.BankAccount;
import com.app.beans.BankAccountTransaction;

/**
 * @author Anudeep
 *
 */
public class BankAccountDAO {
	
	private DataSource dataSource;
	
	public BankAccountDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<BankAccount> getAllBankAccounts() {
		BankAccount account = null;
		List<BankAccount> accounts = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM BANK_ACCOUNT");
			
			while (resultSet.next()) {
				account = new BankAccount();
				account.setAccountNumber(resultSet.getInt("ACC_NUMBER"));
				account.setAccountType(resultSet.getString("ACC_TYPE"));
				account.setName(resultSet.getString("ACC_HOLDER_NAME"));
				account.setEmail(resultSet.getString("ACC_EMAIL"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			Logger.getLogger(BankAccountDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return accounts;
	}
	
	public List<BankAccountTransaction> getTransactionsForAccount(BankAccount account) {
		BankAccountTransaction transaction = null;
		List<BankAccountTransaction> transactions = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM BANK_ACCOUNT_TRANSACTION WHERE ACC_NUMBER = ?");
			statement.setInt(1, account.getAccountNumber());
			ResultSet resultSet = statement.executeQuery(); 
			
			while (resultSet.next()) {
				transaction = new BankAccountTransaction();
				transaction.setTransactionId(resultSet.getInt("TX_ID"));
				transaction.setAccountNumber(resultSet.getInt("ACC_NUMBER"));
				transaction.setTransactionType(resultSet.getString("TRANSACTION_TYPE"));
				transaction.setAmount(resultSet.getInt("AMOUNT"));
				transaction.setTransactionDate(new Date(resultSet.getDate("TRANSACTION_DATE").getTime()));
				transactions.add(transaction);
			}
			
		} catch (SQLException e) {
			Logger.getLogger(BankAccountDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return transactions;
	}
}
