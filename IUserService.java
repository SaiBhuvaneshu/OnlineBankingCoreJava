package com.capgemini.obs.service;

import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;

public interface IUserService {

	public boolean validateUserCredentials(UserBean userBean) throws BankException;

	public UserBean UserDetails(UserBean userBean, long accId) throws BankException;

	public UserBean authenticUser(UserBean loginBean) throws BankException;

	public void setLock(UserBean loginBean, String setLockStatus) throws BankException;

	public void setNewLoginPassword(UserBean loginBean, String loginPassword) throws BankException;

	public UserBean forgotPassword(UserBean loginBean) throws BankException;

	public UserBean authenticUserId(UserBean userBean1) throws BankException;

	public UserBean UserDetails_add(UserBean userBean, long accountId, long accId) throws BankException;

	boolean validateLoginCredentials(UserBean loginBean) throws BankException;

	//public boolean validateLoginCredentials(UserBean loginBean) throws BankException;
	
	public boolean checkTransactionPwd(long account_Id,String tranPwd) throws BankException;


}
