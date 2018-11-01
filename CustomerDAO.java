package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class CustomerDAO implements ICustomerDAO {
	private static Connection conn=null;
	public static Logger logger = Logger.getRootLogger();
	PreparedStatement pstmt;

	/*******************************************************************************************************
	 * - Function Name : addCustomerDetails(CustomerBean customerbean, long accountId) 
	 * - Input Parameters : CustomerBean customerbean, long accountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Adding Customer Details
	 ********************************************************************************************************/

	@Override
	public CustomerBean addCustomerDetails(CustomerBean customerbean, long accountId)
			throws BankException {
		
		PropertyConfigurator.configure("resource//log4j.properties");

		long id = 0;
		int records = 0;
		ResultSet rs = null;
		conn = DBUtil.getConnection();

		try {
			 pstmt = conn
					.prepareStatement(IQueryMapper.INSERT_QUERY);
			
			pstmt.setLong(1, accountId);
			pstmt.setString(2, customerbean.getCustomerName());
			pstmt.setString(3, customerbean.getEmail());
			pstmt.setString(4, customerbean.getAddress());			
			pstmt.setString(5, customerbean.getPanCard());

			records = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(IQueryMapper.SELECT_CUSTOMER_QUERY);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				customerbean.setAccountId(rs.getLong(1));
				customerbean.setCustomerName(rs.getString(2));						
				customerbean.setEmail(rs.getString(3));
				customerbean.setAddress(rs.getString(4));
				customerbean.setPanCard(rs.getString(5));							
			}				
			
			return customerbean;
			
			} catch(SQLException e) {
				throw new BankException(e.getMessage());
				
			}finally{
				try {
					logger.info("Customer Personal details insert successful, 1 record Added");
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
	}
	
	/*******************************************************************************************************
	 * - Function Name : getUserName
	 * - Input Parameters : long accountId 
	 * - Return Type : String 
	 * - Throws : BankException 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 12/10/2018 
	 * - Description : gets user name from database
	 ********************************************************************************************************/
	public String getUserName(long accountId) throws BankException {
		
		conn = DBUtil.getConnection();
		ResultSet rs = null;
		String userName = null;
		try {
			pstmt = conn.prepareStatement(IQueryMapper.GET_USERNAME);
			pstmt.setLong(1,accountId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				userName = rs.getString(1);
				return userName;
			}
			else
			{
				System.out.println("User name not found for this account");
				return null; 
			}
		} catch (SQLException e) {
			throw new BankException("Error while getting user name");
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
	 * - Function Name : CustomerBean viewAddressDetails(Long AccountId) 
	 * - Input Parameters : Long AccountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Customer's Address 
	 ********************************************************************************************************/
	public CustomerBean viewAddressDetails(Long AccountId) throws BankException {

		ResultSet records = null;
		CustomerBean customerbean = null;
		PreparedStatement pstmt = null;
		conn=DBUtil.getConnection();
		try {
			pstmt = conn
					.prepareStatement(IQueryMapper.GET_ADDRESS_QUERY);
			pstmt.setLong(1, AccountId);
			records = pstmt.executeQuery();
			if(records.next())
			{
				customerbean = new CustomerBean();
				customerbean.setAddress(records.getString("ADDRESS"));
				
			}
			if (customerbean != null) {
				logger.info("Address Found Successfully");
				return customerbean;
			} 
			else {
				logger.info("Address Not Found");
				return null;
			} 
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BankException(e.getMessage());
		}
			
			
	}

	/*******************************************************************************************************
	 * - Function Name : CustomerBean viewEmailDetails(long AccountId) 
	 * - Input Parameters : long AccountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Customer's Email Details
	 ********************************************************************************************************/
	@Override
	public CustomerBean viewEmailDetails(long AccountId) throws BankException {

			ResultSet records = null;
			CustomerBean customerbean = null;
			PreparedStatement pstmt = null;
			conn=DBUtil.getConnection();
			try {
				pstmt = conn
						.prepareStatement(IQueryMapper.GET_EMAIL_QUERY);
				pstmt.setLong(1, AccountId);
				records = pstmt.executeQuery();
				if(records.next())
				{
					customerbean = new CustomerBean();
					customerbean.setEmail(records.getString("EMAIL"));
					
				}
				if (customerbean != null) {
					logger.info("email Found Successfully");
					return customerbean;
				} 
				else {
					logger.info("email Not Found");
					return null;
				} 
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new BankException(e.getMessage());
			}
	}
	
	/*******************************************************************************************************
	 * - Function Name : updateCustomerAddress(CustomerBean customerbean) 
	 * - Input Parameters : CustomerBean customerbean
	 * - Return Type : int 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Updating Customer's Address
	 ********************************************************************************************************/
	public int updateCustomerAddress(CustomerBean customerbean) throws BankException {
		PropertyConfigurator.configure("resource//log4j.properties");

		int id = 0;
		int records = 0;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.UPDATE_ADDRESS_QUERY);
			
			pstmt.setString(1, customerbean.getAddress());
			pstmt.setLong(2, customerbean.getAccountId());
			
			records = pstmt.executeUpdate();
			return records;

		} 
		catch (SQLException e) {
			throw new BankException(e.getMessage());
		}
	}
	
	/*******************************************************************************************************
	 * - Function Name : updateEmail(CustomerBean customerbean) 
	 * - Input Parameters : CustomerBean customerbean
	 * - Return Type : int 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Updating Email Id
	 ********************************************************************************************************/
	public int updateEmail(CustomerBean customerbean) throws BankException{
		PropertyConfigurator.configure("resource//log4j.properties");

		int id = 0;
		int records = 0;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.UPDATE_QUERY_EMAIL);
			
			pstmt.setString(1, customerbean.getEmail());
			pstmt.setLong(2, customerbean.getAccountId());
			
			records = pstmt.executeUpdate();
			return records;

		} 
		catch (SQLException e) {
			throw new BankException(e.getMessage());
		}
	}

	/*******************************************************************************************************
	 * - Function Name : fetchCustomerDetails_add(long existingAccId, long newAccId) 
	 * - Input Parameters : long existingAccId, long newAccId
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Fetching Customer Details while adding a new account an existing customer
	 ********************************************************************************************************/
	@Override
	public CustomerBean fetchCustomerDetails_add(long existingAccId, long newAccId)
			throws BankException {
		PropertyConfigurator.configure("resource//log4j.properties");

		CustomerBean customerBean = new CustomerBean();
		//long id = 0;
		int records = 0;
		ResultSet rs = null;
		conn = DBUtil.getConnection();

		try {
			pstmt = conn.prepareStatement(IQueryMapper.SELECT_CUSTOMER_QUERY);
			pstmt.setLong(1, existingAccId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				customerBean.setAccountId(rs.getLong(1));
				customerBean.setCustomerName(rs.getString(2));						
				customerBean.setEmail(rs.getString(3));
				customerBean.setAddress(rs.getString(4));
				customerBean.setPanCard(rs.getString(5));							
			}				
			
			pstmt = conn.prepareStatement(IQueryMapper.INSERT_QUERY);
			
			pstmt.setLong(1, newAccId);
			pstmt.setString(2, customerBean.getCustomerName());
			pstmt.setString(3, customerBean.getEmail());
			pstmt.setString(4, customerBean.getAddress());			
			pstmt.setString(5, customerBean.getPanCard());

			records = pstmt.executeUpdate();
			
			return customerBean;
		}catch(SQLException e) {
			throw new BankException(e.getMessage());
			
			
		}finally{
			try {
				logger.info("Customer Personal details insert successful, 1 record Added");
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
