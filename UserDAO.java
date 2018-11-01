package com.capgemini.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;
import com.capgemini.obs.util.DBUtil;
import com.capgemini.obs.util.IQueryMapper;

public class UserDAO implements IUserDAO{

	private static Connection conn;
	public static Logger logger = Logger.getRootLogger();

	/*******************************************************************************************************
	 * - Function Name : UserDetails(UserBean userBean, long accountId) 
	 * - Input Parameters : UserBean userBean, long accountId 
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Adding user Details
	 ********************************************************************************************************/
	@Override
	public UserBean UserDetails(UserBean userBean, long accountId) throws BankException {
		
		long id = 0;
		long up = 0;
		int records = 0;
		ResultSet rs = null;		
		conn = DBUtil.getConnection();
		
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.INSERT_lOGIN_QUERY);
			
			pstmt.setLong(1, accountId);			
			pstmt.setLong(2, userBean.getUserId());
			pstmt.setString(3, "pword#123");
			pstmt.setString(4, userBean.getSecretQuestion());	
			pstmt.setString(5, userBean.getTransactionPassword());
			pstmt.setString(6, "U");
			pstmt.setString(7, userBean.getSecretAnswer());

			records = pstmt.executeUpdate();
			
			rs=null;
			if (records != 0) {
				logger.info("Details regarding login inserted successfully, 1 user Id created");
				pstmt = conn.prepareStatement(IQueryMapper.UPDATE_lOGIN_QUERY);
				
				pstmt.setLong(1, accountId);
				pstmt.setLong(2, userBean.getUserId());
				
				up = pstmt.executeUpdate();				
				
				if (up==1) {
					pstmt = conn.prepareStatement(IQueryMapper.SELECT_lOGIN_QUERY);		
					
					pstmt.setLong(1, userBean.getUserId());
					
					rs = pstmt.executeQuery();					
					if (rs.next()) {
						userBean.setAccountId(rs.getLong(1));
						userBean.setUserId(rs.getLong(2));						
						userBean.setLoginPassword(rs.getString(3));
						userBean.setSecretQuestion(rs.getString(4));
						userBean.setTransactionPassword(rs.getString(5));
						userBean.setLockStatus(rs.getString(6));	
						userBean.setSecretAnswer(rs.getString(7));
					}
				}
			}			
			pstmt.close();
			return userBean;

		} catch (SQLException e) {
			throw new BankException(e.getMessage());
		}
		
	}

	/*******************************************************************************************************
	 * - Function Name : authenticUser(UserBean loginBean) 
	 * - Input Parameters : UserBean loginBean 
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Authenticating user using userId and Login Password
	 ********************************************************************************************************/
	@Override
	public UserBean authenticUser(UserBean loginBean) throws BankException{
		 
		long up = 0;
		ResultSet rs = null;		
		conn = DBUtil.getConnection();
		
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.SELECT_CHECKlOGIN_QUERY);
			
			pstmt.setLong(1, loginBean.getUserId());		
			pstmt.setString(2, loginBean.getLoginPassword());					
		
			up = pstmt.executeUpdate();				
			
			if (up==1) {
				pstmt = conn.prepareStatement(IQueryMapper.SELECT_lOGIN_QUERY);		
				
				pstmt.setLong(1,  loginBean.getUserId() );
				
				rs = pstmt.executeQuery();					
				while (rs.next()) {
					loginBean.setAccountId(rs.getLong(1));
					loginBean.setUserId(rs.getLong(2));						
					loginBean.setLoginPassword(rs.getString(3));
					loginBean.setSecretQuestion(rs.getString(4));
					loginBean.setTransactionPassword(rs.getString(5));
					loginBean.setLockStatus(rs.getString(6));	
					loginBean.setSecretAnswer(rs.getString(7));
				}
			}else{				
				return null;
			}
			pstmt.close();	

	} catch (SQLException e) {
		throw new BankException(e.getMessage());
	}
	return loginBean;
		
		}

	/*******************************************************************************************************
	 * - Function Name :setLock(UserBean loginBean, String LockStatus) 
	 * - Input Parameters : UserBean loginBean, String LockStatus 
	 * - Return Type : void 
	 * - Throws : {@link CarRentException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Locking Account after three attempts
	 ********************************************************************************************************/
	@Override
	public void setLock(UserBean loginBean, String LockStatus) throws BankException{

		ResultSet rs = null;		
		conn = DBUtil.getConnection();
		
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.UPDATE_lOCK_QUERY);
			
			pstmt.setString(1, LockStatus);		
			pstmt.setLong(2, loginBean.getUserId());		
		
			rs = pstmt.executeQuery();					
			
			}catch (SQLException e) {
				throw new BankException(e.getMessage());
			}
	
	}

	/*******************************************************************************************************
	 * - Function Name :setNewLoginPassword(UserBean loginBean, String loginPassword) 
	 * - Input Parameters : UserBean loginBean, String loginPassword 
	 * - Return Type : void 
	 * - Throws : {@link CarRentException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Setting new login password after the user has logged in
	 ********************************************************************************************************/
	@Override
	public void setNewLoginPassword(UserBean loginBean, String loginPassword)
			throws BankException {

		int n;
		conn = DBUtil.getConnection();
		
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.UPDATE_LOGIN_PASSWORD_QUERY);
			
			pstmt.setString(1, loginPassword);		
			pstmt.setLong(2, loginBean.getUserId());		
		
			n = pstmt.executeUpdate();					
			}catch (SQLException e) {
				throw new BankException(e.getMessage());
			}
		
	}

	/*******************************************************************************************************
	 * - Function Name :forgotPassword(UserBean userForgotPass) 
	 * - Input Parameters : UserBean userForgotPass 
	 * - Return Type : UserBean 
	 * - Throws : {@link CarRentException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Setting a new login password while login if user chooses forgot password option
	 ********************************************************************************************************/
	@Override
	public UserBean forgotPassword(UserBean userForgotPass) throws BankException {
		
		long up = 0;
		ResultSet rs = null;		
		conn = DBUtil.getConnection();
		PreparedStatement pstmt = null;
		PropertyConfigurator.configure("resource//log4j.properties");
		
       UserBean user = new UserBean();
		try {
			pstmt = conn
					.prepareStatement(IQueryMapper.SELECT_FORGOT_PASSWORD_QUERY);
								
			pstmt.setLong(1, userForgotPass.getUserId());
		System.out.println(userForgotPass.getUserId()+"in dao before if");
		
			rs= pstmt.executeQuery();
			
			System.out.println(userForgotPass.getUserId()+"in dao after execute");
			if (rs.next()) {
				pstmt = conn.prepareStatement(IQueryMapper.SELECT_lOGIN_QUERY);		
				
				pstmt.setLong(1,  userForgotPass.getUserId() );
				
			List<UserBean> accList = new ArrayList<UserBean>();
			do{
				System.out.println(rs.getLong(1)+"acc id");
				user.setAccountId(rs.getLong(1));
				user.setUserId(rs.getLong(2));						
				user.setLoginPassword(rs.getString(3));
				user.setSecretQuestion(rs.getString(4));
				user.setTransactionPassword(rs.getString(5));
				user.setLockStatus(rs.getString(6));	
				user.setSecretAnswer(rs.getString(7));
				accList.add(user);
				System.out.println(accList.get(0).toString());
			}while(rs.next());
			System.out.println(accList.get(0).toString());
			return accList.get(0);
			
		}else{	
			return new UserBean();
		}
	//-----------------------------------------------------------------------		
	/*		up = pstmt.executeUpdate();
			if (up==1) {
				pstmt = conn.prepareStatement(IQueryMapper.SELECT_lOGIN_QUERY);		
				
				pstmt.setLong(1,  userForgotPass.getUserId() );
				
				rs = pstmt.executeQuery();					
				while (rs.next()) {
					user.setAccountId(rs.getLong(1));
					user.setUserId(rs.getLong(2));						
					user.setLoginPassword(rs.getString(3));
					user.setSecretQuestion(rs.getString(4));
					user.setTransactionPassword(rs.getString(5));
					user.setLockStatus(rs.getString(6));	
					user.setSecretAnswer(rs.getString(7));
				}
				return user;
			}else{	
				System.out.println(user.getUserId()+"In dao else");
				return new UserBean();
			}*/

	} catch (SQLException e) {
		e.printStackTrace();
		throw new BankException(e.getMessage());
		
	}
		finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/*******************************************************************************************************
	 * - Function Name : authenticUserId
	 * - Input Parameters : UserBean checkUserId
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Checking whether there a userId already exists in the bank database
	 ********************************************************************************************************/
	@Override
	public UserBean authenticUserId(UserBean checkUserId) throws BankException {
		
		ResultSet rs = null;	
		conn = DBUtil.getConnection();
		UserBean bean =null;
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.FETCH_USERID_TO_ADD_ACCOUNT_QUERY);
					
			pstmt.setLong(1, checkUserId.getUserId());			
			rs = pstmt.executeQuery();			
				
				while (rs.next()) {
					bean = new UserBean();
					
					bean.setAccountId(rs.getLong(1));
					bean.setUserId(rs.getLong(2));
					bean.setLoginPassword(rs.getString(3));
					bean.setSecretQuestion(rs.getString(4));
					bean.setTransactionPassword(rs.getString(5));
					bean.setLockStatus(rs.getString(6));	
					bean.setSecretAnswer(rs.getString(7));	
					
				}	
				pstmt.close();	
				conn.close();
				return bean; 
		

	} catch (SQLException e) {
		//throw new BankException(e.getMessage());
		e.printStackTrace();
	}
		return null;
	}

	/*******************************************************************************************************
	 * - Function Name : UserDetails_add
	 * - Input Parameters : UserBean userBean, long existingAccId, long newAccId
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Adding User Details When creating a new account for an existing customer
	 ********************************************************************************************************/
	@Override
	public UserBean UserDetails_add(UserBean userBean, long existingAccId,
			long newAccId) throws BankException {
		
		PropertyConfigurator.configure("resource//log4j.properties");
		UserBean newUser = new UserBean();
		conn = DBUtil.getConnection();
		
		ResultSet rs = null;	
		int records = 0;

		try {

			PreparedStatement pstmt = conn.prepareStatement(IQueryMapper.SELECT_USER_QUERY_NEW_ACCID);
			pstmt.setLong(1, existingAccId);			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				newUser.setAccountId(rs.getLong(1));
				newUser.setUserId(rs.getLong(2));						
				newUser.setLoginPassword(rs.getString(3));
				newUser.setSecretQuestion(rs.getString(4));
				newUser.setTransactionPassword(rs.getString(5));
				newUser.setLockStatus(rs.getString(6));	
				newUser.setSecretAnswer(rs.getString(7));							
			}			

			pstmt = conn.prepareStatement(IQueryMapper.INSERT_NEW_ACC_USER_QUERY);
			userBean.setAccountId(newAccId);
			userBean.setLoginPassword(newUser.getLoginPassword());
			userBean.setLockStatus("U");
			userBean.setSecretQuestion(newUser.getSecretQuestion());
			userBean.setSecretAnswer(newUser.getSecretAnswer());			
			
			pstmt.setLong(1, userBean.getAccountId());
			pstmt.setLong(2, userBean.getUserId());
			pstmt.setString(3, userBean.getLoginPassword());
			pstmt.setString(4, userBean.getSecretQuestion());	
			pstmt.setString(5, userBean.getTransactionPassword());
			pstmt.setString(6, userBean.getLockStatus());
			pstmt.setString(7, userBean.getSecretAnswer());
 
			records = pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			return userBean;
		}catch(SQLException e) {
			throw new BankException(e.getMessage());
			
			
		}finally{			
				logger.info("Customer Personal details insert successful, 1 record Added");		
		}
		
	}

	/*******************************************************************************************************
	 * - Function Name : getMultipleAccounts(UserBean loginCredentials)
	 * - Input Parameters : UserBean loginCredentials 
	 * - Return Type : List<UserBean> 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Fetching multiple accounts for a given userId 
	 ********************************************************************************************************/
	@Override
	public List<UserBean> getMultipleAccounts(UserBean loginBean)
			throws BankException {
		long up = 0;
		ResultSet rs = null;		
		conn = DBUtil.getConnection();
		
		PropertyConfigurator.configure("resource//log4j.properties");
		

		try {
			PreparedStatement pstmt = conn
					.prepareStatement(IQueryMapper.SELECT_CHECKlOGIN_QUERY);
			
			pstmt.setLong(1, loginBean.getUserId());		
			pstmt.setString(2, loginBean.getLoginPassword());					
			
			rs = pstmt.executeQuery();
			
			List<UserBean> arrayList = new ArrayList<UserBean>();
			UserBean account = null;		
			
			while(rs.next())
			{
				account = new UserBean();
				account.setAccountId(rs.getLong(1));
				account.setUserId(rs.getLong(2));						
				account.setLoginPassword(rs.getString(3));
				account.setSecretQuestion(rs.getString(4));
				account.setTransactionPassword(rs.getString(5));
				account.setLockStatus(rs.getString(6));	
				account.setSecretAnswer(rs.getString(7));
				arrayList.add(account);
			}
			
			if(arrayList.isEmpty())
			{
				logger.info("No such account available in bank database");
				return null;
			}
			else
			{
				logger.info("Multiple Accounts are retrieved");
				return arrayList;
			}
			
			} catch (SQLException e) {
						
					logger.error("error occured while retreiving accounts");
					throw new BankException("error occured while retriving accounts");
				}
				finally
				{
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("error while closing connection");						
					}
				}
		
	}
	
	/***************************************************************************************
	 - Function Name	:	checkTransactionPwd(long account_Id, String tranPwd)
	 - Input Parameters	:	long account_Id, String tranPwd
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Check transaction password of a user
	 ********************************************************************************************************/
	@Override
	public boolean checkTransactionPwd(long account_Id, String tranPwd) throws BankException {
		conn = DBUtil.getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String password = null;
		try {
			
			preparedStatement = conn.prepareStatement(IQueryMapper.CHECK_TRANSACTION_PWD);
			
			preparedStatement.setLong(1, account_Id);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				
				if(tranPwd.equals(resultSet.getString(1))){
					
					return true;
				}else{
					
					return false;
				}
			}
			else{
				
				return false;
			}
			
		} catch (SQLException e) {
			//logger.error("Invalid Transaction Password");
			System.err.println("Invalid transaction Password");
			throw new BankException("Tehnical problem occured. Refer log");
			
		}
		finally{
			try {
				resultSet.close();
				preparedStatement.close();
				conn.close();
			}catch (SQLException e) {
				//logger.error(e.getMessage());
				throw new BankException("Error in closing db connection");
				
			}
		}

	}

}
