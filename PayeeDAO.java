package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class PayeeDAO implements IPayeeDAO {
	
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	public static Logger logger = Logger.getRootLogger();
	
	/***************************************************************************************
	 - Function Name	:	checkPayee(PayeeBean payeeBean)
	 - Input Parameters	:	PayeeBean payeeBean
	 - Return Type		:	int
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Checks payee is registered or not in user account
	 ********************************************************************************************************/
	@Override
	public int checkPayee(PayeeBean payeeBean) throws BankException {
		
		conn = DBUtil.getConnection();
		PropertyConfigurator.configure("resource//log4j.properties");
		
		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.SELECT_PAYEE_ID);
			
			preparedStatement.setLong(1, payeeBean.getAccountId());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			ResultSetMetaData meta = resultSet.getMetaData();
			int colCount = meta.getColumnCount();
			int col,set1 = 0,set2 = 0;
			int arr;
			String nick;
			
			while(resultSet.next()){
				for (col=1; col <= colCount; col++) 
			    {
			        	if(col==1){
			        		int value = resultSet.getInt(col);
			        		//arr= ((BigInteger) value).intValueExact();
				        	if(value == payeeBean.getPayeeAccountId())
				        		set1 = 1;
				        	else
				        		set1 = 0;
			        	}else if(col ==2){
			        		nick = resultSet.getString(col);
			        		if(nick.equalsIgnoreCase(payeeBean.getNickName()))
			        			set2 = 1;
			        		else
			        			set2 = 0;
			        	}
			        	
			          
			        
			    }//end of for
				if(set1 ==1 && set2 == 1)
					return 0;
				else if(set1 ==1 && set2 == 0)
					return 2;
				else if(set1 == 0 && set2 ==0)
					continue;
				
			}
			return 1;
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BankException("Tehnical problem occured. Refer log");
		}
		finally{
			try {
				
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new BankException("Error in closing db connection");
				
			}
		}
		
	}
	
	/***************************************************************************************
	 - Function Name	:	addPayee(PayeeBean payeeBean)
	 - Input Parameters	:	PayeeBean payeeBean
	 - Return Type		:	PayeeBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Register payee into user account
	 ********************************************************************************************************/
	@Override
	public PayeeBean addPayee(PayeeBean payeeBean) throws BankException {
		
		conn = DBUtil.getConnection();
		
		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.INSERT_PAYEE);
			
			preparedStatement.setLong(1,payeeBean.getAccountId());
			preparedStatement.setLong(2,payeeBean.getPayeeAccountId());
			preparedStatement.setString(3,payeeBean.getNickName());
			
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			System.err.println("Payee Registration failed");
			System.out.println(e.getMessage());
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
		return payeeBean;

	}
	
	/***************************************************************************************
	 - Function Name	:	payPayee(FundTransferBean fundTransfer)
	 - Input Parameters	:	FundTransferBean fundTransfer
	 - Return Type		:	FundTransferBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Transfer fund to payee of same bank
	 ********************************************************************************************************/
	@Override
	public FundTransferBean payPayee(FundTransferBean fundTransfer) throws BankException {
		
		conn = DBUtil.getConnection();
		
		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.FUND_TRANSFER_ACCOUNT);
			preparedStatement.setLong(1, fundTransfer.getAccountId());
			preparedStatement.setLong(2, fundTransfer.getPayeeAccountId());
			preparedStatement.setInt(3, fundTransfer.getTransferAmount());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("Error in payment");
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
		return fundTransfer;

	}

}
