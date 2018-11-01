package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class FundTransferDAO implements IFundTransferDAO {

	static Logger logger = Logger.getLogger("logfile");
	
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	int curr_bal,payee_curr_bal;
	int max_limit=0;
	
	public FundTransferDAO(){
		PropertyConfigurator.configure("resource//log4j.properties");
		
	}
	
	
		/*******************************************************************************************************
	 * - Function Name : checkMaxFunds(long account_ID, int other_bank_amount) 
	 * - Input Parameters : long account_ID, int other_bank_amount 
	 * - Return Type : boolean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Checking Maximum funds
	 ********************************************************************************************************/
	@Override
	public boolean checkMaxFunds(long account_ID, int other_bank_amount) throws BankException {
		
		conn = DBUtil.getConnection();
		try {
			
			//Date today = LocalDate.now();
			LocalDate date = LocalDate.now();
			Date today = Date.valueOf(date);
			
			preparedStatement = conn.prepareStatement(IQueryMapper.CHECK_MAX_LIMIT);
			preparedStatement.setLong(1, account_ID);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()){
				
				
				Date dateTransfer = resultSet.getDate(1);
				
				if(today.equals(dateTransfer)){
				
					max_limit += resultSet.getInt(2);
				}
			}
			
			
			if(max_limit>=1000000)
				return false;
			else
				return true;
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			System.err.println("Error in checking for the total fund transferred today");
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

		/*******************************************************************************************************
	 * - Function Name : TransferSameAccount(FundTransferBean fundTransferBean) 
	 * - Input Parameters : FundTransferBean fundTransferBean 
	 * - Return Type : FundTransferBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Transfer to Same account
	 ********************************************************************************************************/
	@Override
	public FundTransferBean TransferSameAccount(FundTransferBean fundTransferBean) throws BankException {
		
		conn = DBUtil.getConnection();
		int status = 0;
		try {
			preparedStatement = conn.prepareStatement(IQueryMapper.FUND_TRANSFER_ACCOUNT);
			
			preparedStatement.setLong(1, fundTransferBean.getAccountId());
			preparedStatement.setLong(2, fundTransferBean.getPayeeAccountId());
			preparedStatement.setInt(3, fundTransferBean.getTransferAmount());
			
			status = preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			logger.error("Database Problem : Data not stored");
			throw new BankException("DBProblem : data not stored" + e.getMessage());
		}
		finally{
			try {
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("Connection not closed");
			}
		}
		return fundTransferBean;
	}


}
