                          package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.AccountMasterBean;
import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class AccountMasterDAO implements IAccountMasterDAO{

	private static Connection conn;
	public static Logger logger = Logger.getRootLogger();
 
	/*******************************************************************************************************
	 * - Function Name : addAccountDetails(AccountMasterBean accountMasterBean) 
	 * - Input Parameters : AccountMasterBean accountMasterBean 
	 * - Return Type : long 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Adding Account Details
	 ********************************************************************************************************/
	@Override
	public long addAccountDetails(AccountMasterBean accountMasterBean)
			throws BankException{
		
		PropertyConfigurator.configure("resource//log4j.properties");

		long id = 0;
		int records = 0;
		ResultSet rs = null;
		conn = DBUtil.getConnection();

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.INSERT_CREATE_ACC_QUERY);
			
			pstmt.setString(1, accountMasterBean.getAccountType());
			pstmt.setInt(2, accountMasterBean.getAccountBalance());

			records = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(IQueryMapper.ACCID_QUERY_SEQUENCE);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				id = rs.getLong(1);
			}

			pstmt.close();

			if (records != 0) {
				logger.info("Account Details insert successful, 1 record Added");
				conn.close();
				return id;
			}

			return id;

		} catch (SQLException e) {
			throw new BankException(e.getMessage());
		}
		
	}

	
	/***************************************************************************************
	 - Function Name	:	checkAccountExists(long accountId)
	 - Input Parameters	:	long accountI
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Checks whether account exists or not
	 ********************************************************************************************************/

	@Override
	public boolean checkAccountExists(long accountId) throws BankException {
			
		conn = DBUtil.getConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(IQueryMapper.CHECK_ACCOUNT_EXIST);
			pstmt.setLong(1,accountId);
			
			ResultSet resultSet = pstmt.executeQuery();
			
			if(resultSet.next())
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			logger.error("DB problem");
			throw new BankException("Technical error..Refer log");
		}
	}
	
	/***************************************************************************************
	 - Function Name	:	checkAccountBalance(long account_ID, int other_bank_amount)
	 - Input Parameters	:	long account_ID, int other_bank_amount
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Checks sufficiency of account balance
	 ********************************************************************************************************/
	@Override
	public boolean checkAccountBalance(long account_ID, int other_bank_amount) throws BankException {
		
		conn = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.CHECK_ACCOUNT_BALANCE);
			preparedStatement.setLong(1, account_ID);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				
				if(other_bank_amount <= resultSet.getInt(1)){
					return true;
				}else{
					return false;
				}
			}else{
				
				return false;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BankException("Tehnical problem occured. Refer log");
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

	}

	
}
