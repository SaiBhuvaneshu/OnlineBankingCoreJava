package com.capgemini.obs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.dao.CustomerDAO;
import com.capgemini.obs.dao.IUserDAO;
import com.capgemini.obs.dao.UserDAO;
import com.capgemini.obs.exceptions.BankException;

public class UserService implements IUserService {

	static IUserDAO userDao;
	UserBean userBean;
	UserBean loginBean;
	
	//--------------------Add User Details------------------------------------
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
	public UserBean UserDetails(UserBean userBean, long accId) throws BankException {
		
		userDao = new UserDAO();

		try {		
			userBean = userDao.UserDetails(userBean, accId);

		} catch (BankException e) {
			e.printStackTrace();
		}
		return userBean;
	}
	
	//--------------------Validate User Details while SignUp------------------------------------
	/*******************************************************************************************************
	 * - Function Name :validateUserCredentials(UserBean userBean) 
	 * - Input Parameters : UserBean userBean - Return Type : boolean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Validating details
	 ********************************************************************************************************/
	@Override
	public boolean validateUserCredentials(UserBean userBean) throws BankException {
		
		List<String> validationErrors = new ArrayList<String>();

		// Validating UserId
				if (!(isValidUserId(userBean.getUserId()))) {
								validationErrors.add("\n UserId should have 4-10 digits only! \n");
				}
		
		// Validating TransactionPassword
		if (!(isValidTransactionPassword(userBean.getTransactionPassword()))) {
			validationErrors.add("\n Invalid Transaction Password! \n");
		}
		
		if (!validationErrors.isEmpty())
			throw new BankException(validationErrors + "");
		else
			return true;
	}
	
	private boolean isValidUserId(long userId) {
		return (userId > 999 && userId <= 999999999 );
		
	}
	
	private static boolean isValidTransactionPassword(String transPassword) {
		Pattern namePattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+!*=]).{6,}$");
		Matcher nameMatcher = namePattern.matcher(transPassword);
		if(nameMatcher.matches())
		{
			if(transPassword.length()>10)
			{
				return false;
			}
		}
		return nameMatcher.matches();
	}
	
	//--------------------Validate User Details while LoginIn------------------------------------
	/*******************************************************************************************************
	 * - Function Name : authenticUser(UserBean loginBean) 
	 * - Input Parameters : UserBean loginBean 
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Authenticating user 
	 ********************************************************************************************************/
	public UserBean authenticUser(UserBean loginBean) throws BankException {

		userDao = new UserDAO();

		try {		
			loginBean= userDao.authenticUser(loginBean);			

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return loginBean;	
		}

	@Override
	public boolean validateLoginCredentials(UserBean loginBean) throws BankException {
		List<String> validationErrors = new ArrayList<String>();

		// Validating UserId
		if (!(isValidUserId(loginBean.getUserId()))) {
						validationErrors.add("\n UserId should have digits upto 10 only! \n");
		}
			
		// Validating LoginPassword
		if (!(isValidLoginPassword(loginBean.getLoginPassword()))) {
			validationErrors.add("\n Login Password Format Error! \n");
		}
		if (!validationErrors.isEmpty())
			throw new BankException(validationErrors + "");
		else
			return true;
	}

	private static boolean isValidLoginPassword(String loginPassword) {
		Pattern namePattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+!*=]).{6,}$");
		Matcher nameMatcher = namePattern.matcher(loginPassword);
		if(nameMatcher.matches())
		{
			if(loginPassword.length()>10)
			{
				return false;
			}
		}
		return nameMatcher.matches();
	}

	/*******************************************************************************************************
	 * - Function Name :setLock(UserBean loginBean, String LockStatus) 
	 * - Input Parameters : UserBean loginBean, String LockStatus 
	 * - Return Type : void 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Locking Account
	 ********************************************************************************************************/
	@Override
	public void setLock(UserBean loginBean, String setLockStatus) throws BankException {

		try {		
			userDao.setLock(loginBean, setLockStatus);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*******************************************************************************************************
	 * - Function Name :setNewLoginPassword(UserBean loginBean, String loginPassword) 
	 * - Input Parameters : UserBean loginBean, String loginPassword 
	 * - Return Type : void 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Setting new login password when already logged in
	 ********************************************************************************************************/
	@Override
	public void setNewLoginPassword(UserBean loginBean, String loginPassword)
			throws BankException {
		
		userDao = new UserDAO();
		
		try {	
			userDao.setNewLoginPassword(loginBean, loginPassword);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*******************************************************************************************************
	 * - Function Name :forgotPassword(UserBean userForgotPass) 
	 * - Input Parameters : UserBean userForgotPass 
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Setting new login password while login
	 ********************************************************************************************************/
	@Override
	public UserBean forgotPassword(UserBean userForgotPass) throws BankException {
		userDao = new UserDAO();

		try {		
			userForgotPass = userDao.forgotPassword(userForgotPass);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userForgotPass;
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
		userDao = new UserDAO();

		try {		
			checkUserId = userDao.authenticUserId(checkUserId);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkUserId;
	}

	/*******************************************************************************************************
	 * - Function Name : UserDetails_add
	 * - Input Parameters : UserBean userBean, long existingAccId, long accId
	 * - Return Type : UserBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/08/2018 
	 * - Description : Adding User Details When creating a new account for an existing customer
	 ********************************************************************************************************/
	@Override
	public UserBean UserDetails_add(UserBean userBean, long existingAccId,
			long accId) throws BankException {
		userDao = new UserDAO();

		try {		
			userBean = userDao.UserDetails_add(userBean, existingAccId, accId);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBean;
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
	public static List<UserBean> getMultipleAccounts(UserBean loginCredentials) throws BankException {
		userDao = new UserDAO();
		List<UserBean> multipleAccList = new ArrayList<UserBean>(); 
		try {		
			multipleAccList= userDao.getMultipleAccounts(loginCredentials);			

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return multipleAccList;	
	}

	/*******************************************************************************************************
	 - Function Name	:	checkTransactionPwd(long account_Id, String tranPwd)
	 - Input Parameters	:	long account_Id, String tranPwd
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	07/10/2018
	 - Description		:	Calls dao method checkTransactionPwd(long account_Id, String tranPwd)
	 ********************************************************************************************************/
	@Override
	public boolean checkTransactionPwd(long account_Id, String tranPwd) throws BankException {
		userDao = new UserDAO();
		boolean checkPwd = userDao.checkTransactionPwd(account_Id,tranPwd);
		return checkPwd;
	}
}