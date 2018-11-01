package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class TransactionDAO implements ITransactionDAO {

	private static Connection conn;
	public static Logger logger = Logger.getRootLogger();
	int curr_bal,payee_curr_bal;

	/*******************************************************************************************************
	 * - Function Name : viewDailyTransactionDetails(LocalDate dateOfTransaction) 
	 * - Input Parameters : LocalDate dateOfTransaction
	 * - Return Type : List 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing daily Transactions By Bank Admin
	 ********************************************************************************************************/
	@Override
	public List<TransactionsBean> viewDailyTransactionDetails(
			LocalDate dateOfTransaction) throws BankException {
		ResultSet records = null;
		TransactionsBean transactionsbean = null;
		PreparedStatement pstmt = null;
		List<TransactionsBean> transactionlist = new ArrayList<TransactionsBean>();
		conn = DBUtil.getConnection();
		try {
			pstmt = conn
					.prepareStatement(IQueryMapper.GET_DAILY_TRANSACTION_QUERY);
			pstmt.setDate(1, Date.valueOf(dateOfTransaction));
			pstmt.setDate(2, Date.valueOf(dateOfTransaction.plusDays(1)));
			records = pstmt.executeQuery();
			while (records.next()) {
				transactionsbean = new TransactionsBean();
				
				transactionsbean.setTransactionId(records.getInt(1));
				transactionsbean.setTranDescription(records.getString(2));
				transactionsbean.setDateOfTransaction(records.getDate(3).toLocalDate());
				transactionsbean.setTransactionType(records.getString(4));
				transactionsbean.setTranAmount(records.getInt(5));
				transactionsbean.setAccountId(records.getLong(6));
				transactionlist.add(transactionsbean);
			}
			if (!transactionlist.isEmpty()) { 
				logger.info("Transaction Details Found Successfully");
				return transactionlist;
			} else {
				logger.info("Transaction Details Not Found");
				return transactionlist;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage()); 
			throw new BankException(e.getMessage());
		}
	}

	/*******************************************************************************************************
	 * - Function Name : viewMyTransactionDetails(LocalDate stdate, LocalDate endate) 
	 * - Input Parameters : LocalDate stdate, LocalDate endate
	 * - Return Type : List 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Monthly and yearly Transaction
	 ********************************************************************************************************/
	@Override
	public List<TransactionsBean> viewMyTransactionDetails(LocalDate stdate,
			LocalDate endate) throws BankException {
		ResultSet records = null;
		TransactionsBean transactionsbean = null;
		PreparedStatement pstmt = null;
		List<TransactionsBean> transactionlist = new ArrayList<TransactionsBean>();
		conn = DBUtil.getConnection();
		try {
			pstmt = conn
					.prepareStatement(IQueryMapper.GET_MY_TRANSACTION_QUERY);
			pstmt.setDate(1, Date.valueOf(stdate));
			pstmt.setDate(2, Date.valueOf(endate.plusDays(1)));
			records = pstmt.executeQuery();
			while (records.next()) {
				transactionsbean = new TransactionsBean();
				transactionsbean.setTransactionId(records.getInt(1));
				transactionsbean.setTranDescription(records.getString(2));
				transactionsbean.setDateOfTransaction(records.getDate(3)
						.toLocalDate());
				transactionsbean.setTransactionType(records.getString(4));
				transactionsbean.setTranAmount(records.getInt(5));
				transactionsbean.setAccountId(records.getLong(6));
				transactionlist.add(transactionsbean);
			}
			if (!transactionlist.isEmpty()) {
				logger.info("Transaction Details Found Successfully");
			} else {
				logger.info("Transaction Details Not Found");
			}
			return transactionlist;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BankException(e.getMessage());
		}
	}
	
	/***************************************************************************************
	 - Function Name	:	updateTransaction(long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount)
	 - Input Parameters	:	long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	update credit and debit in transaction table
	 ********************************************************************************************************/
	@Override
	public boolean updateTransaction(long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount)
			throws BankException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		conn = DBUtil.getConnection();
		int records_accHolder=0;
		int records_payeeHolder=0;
		int records_updated = 0;
		int flag1=0; int flag2=0;
		

		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.INSERT_TRANSFER_TABLE);
			
			preparedStatement.setString(1,tran_desc);
			preparedStatement.setString(2,"d");
			preparedStatement.setInt(3,other_bank_amount);
			preparedStatement.setLong(4, account_ID);
			
			records_accHolder = preparedStatement.executeUpdate();
			
			
			preparedStatement = conn.prepareStatement(IQueryMapper.CHECK_ACCOUNT_BALANCE);
			preparedStatement.setLong(1, account_ID);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				curr_bal = resultSet.getInt(1);
			}
			
			preparedStatement = conn.prepareStatement(IQueryMapper.UPDATE_ACCOUNT_BAL);
			int update_bal = curr_bal - other_bank_amount;
			preparedStatement.setInt(1, update_bal);
			preparedStatement.setLong(2, account_ID);
			
			preparedStatement.executeUpdate();
			
			if(records_accHolder>0 && records_updated>0)
				flag1 = 1;
			
		} catch (SQLException e) {
			logger.error("Database Problem : Data not stored");
			System.err.println("Error in updating in transaction table");
			throw new BankException("Tehnical problem occured. Refer log" +e.getMessage());
		}
		
		try {
			
			preparedStatement = conn.prepareStatement(IQueryMapper.INSERT_TRANSFER_TABLE);
			preparedStatement.setString(1,tran_desc);
			preparedStatement.setString(2,"c");
			preparedStatement.setInt(3,other_bank_amount);
			preparedStatement.setLong(4, payee_Account_Id);
			
			records_payeeHolder = preparedStatement.executeUpdate();
			
			preparedStatement = conn.prepareStatement(IQueryMapper.CHECK_ACCOUNT_BALANCE);
			preparedStatement.setLong(1, payee_Account_Id);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				
				payee_curr_bal = resultSet.getInt(1);
			}
			
			
			preparedStatement = conn.prepareStatement(IQueryMapper.UPDATE_ACCOUNT_BAL);
			int update_payee_bal = payee_curr_bal + other_bank_amount;
			preparedStatement.setInt(1, update_payee_bal);
			preparedStatement.setLong(2, payee_Account_Id);
			
			preparedStatement.executeUpdate();
			if(records_payeeHolder>0)
				flag2 = 1;
			
		} catch (SQLException e) {
			
			logger.error("Database Problem : Data not stored");
			System.err.println("Could't update record in Transaction table of Payee Account");
			throw new BankException("Tehnical problem occured. Refer log" +e.getMessage());
			
		}
		finally{
			try {
				preparedStatement.close();
				conn.close();
			}catch (SQLException e) {
				logger.error(e.getMessage());
				throw new BankException("Error in closing db connection");
				
			}
		}
		if(flag1==1&&flag2==1)
			return true;
		else
			return false;


	}

}
