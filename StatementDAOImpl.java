package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.StatementBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class StatementDAOImpl implements IStatementDAO {

	
	static Logger logger = Logger.getLogger("logfile");
	Connection conn = null;
	PreparedStatement pstmt= null;
	
	ResultSet rs = null;
	public StatementDAOImpl() {

		PropertyConfigurator.configure("resource//log4j.properties");
	}
	
	/*******************************************************************************************************
	 - Function Name	:	viewMiniStatement
	 - Input Parameters	:	StatementBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	19/09/2018
	 - Description		:	view mini statement of an account from database  
	 ********************************************************************************************************/	
	@Override
	public List<TransactionsBean> viewMiniStatement(StatementBean stmt) throws BankException{
		
		conn = DBUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(IQueryMapper.VIEW_MINI_STATEMENT);
			pstmt.setLong(1, stmt.getAccountId());
			ResultSet rs = pstmt.executeQuery();
			
			List<TransactionsBean> arrayList = new ArrayList<TransactionsBean>();
			TransactionsBean transaction = null;
			while(rs.next())
			{
				transaction = new TransactionsBean();
				transaction.setTransactionId(rs.getInt(1));
				transaction.setTranDescription(rs.getString(2));
				transaction.setDateOfTransaction(rs.getDate(3).toLocalDate());
				transaction.setTransactionType(rs.getString(4));
				transaction.setTranAmount(rs.getInt(5));
				arrayList.add(transaction);
			}
			if(arrayList.isEmpty())
			{
				logger.info("No records available for Mini Statement");
				return null;
			}
			else
			{
				logger.info("Mini Statement is retrieved");
				return arrayList;
			}
		} catch (SQLException e) {
			
			logger.error("error occured while retriving Mini Statement");
			throw new BankException("error occured while retriving Mini Statement");
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("error while closing connection");
				//e.printStackTrace();
			}
		}
	}

	/*******************************************************************************************************
	 - Function Name	:	viewDetailedStatement
	 - Input Parameters	:	StatementBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	19/09/2018
	 - Description		:	view detailed statement of an account over a time period from database  
	 ********************************************************************************************************/	
	@Override
	public List<TransactionsBean> viewDetailedStatement(StatementBean stmt) throws BankException{
		
		conn = DBUtil.getConnection();
		PreparedStatement pst =null;
		ResultSet resultAccounts = null;
		try {
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        LocalDate startDate = stmt.getStartDate();
	        LocalDate endDate = stmt.getEndDate();
	        List<TransactionsBean> arrayList = new ArrayList<TransactionsBean>();
			TransactionsBean transaction = null;
			List<CustomerBean> multipleAccounts = new ArrayList<CustomerBean>();
			CustomerBean customer = null;
			
			pst = conn.prepareStatement(IQueryMapper.GET_ACCOUNTS);
			pst.setLong(1, stmt.getAccountId());
			resultAccounts = pst.executeQuery();
			
			if(resultAccounts != null)
			{
				//System.out.println("problem");
			}
			while(resultAccounts.next())
			{
				customer = new CustomerBean();
				customer.setAccountId(resultAccounts.getLong(1));
				multipleAccounts.add(customer);
			}

			if(multipleAccounts.isEmpty())
			{
				logger.info("No customer available for given account number");
			}
			else
			{
				Iterator<CustomerBean> itr = multipleAccounts.iterator();
				while(itr.hasNext()){
					
					pstmt = conn.prepareStatement(IQueryMapper.VIEW_DETAIL_STATEMENT);
					pstmt.setDate(1, Date.valueOf(startDate));
					pstmt.setDate(2, Date.valueOf(endDate.plusDays(1)));
					pstmt.setLong(3, (itr.next()).getAccountId());
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())
					{
						transaction = new TransactionsBean();
						transaction.setTransactionId(rs.getInt(1));
						transaction.setTranDescription(rs.getString(2));
						transaction.setDateOfTransaction(rs.getDate(3).toLocalDate());
						transaction.setTransactionType(rs.getString(4));
						transaction.setTranAmount(rs.getInt(5));
						transaction.setAccountId(rs.getLong(6));
						arrayList.add(transaction);
					}
					
				}
			}
	        
			if(arrayList.isEmpty())
			{
				logger.info("No records available for Detailed Statement");
				return null;
			}
			else
			{
				logger.info("Detailed Statement is retrieved");
				return arrayList;
			}
		} catch (SQLException e) {
			logger.error("error occured while retriving Detailed Statement");
			throw new BankException("error occured while retriving Detailed Statement");
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("error while closing connection");
				//e.printStackTrace();
			}
		}
	}

}
