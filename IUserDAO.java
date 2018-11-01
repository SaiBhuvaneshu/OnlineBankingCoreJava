package com.capgemini.obs.dao;

import java.util.List;

import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;

public interface IUserDAO {

	public UserBean UserDetails(UserBean userBean, long accountId) throws BankException;

	public UserBean authenticUser(UserBean loginBean) throws BankException;

	public void setLock(UserBean loginBean, String setLockStatus) throws BankException;

	public void setNewLoginPassword(UserBean loginBean, String loginPassword) throws BankException;

	public UserBean forgotPassword(UserBean userForgotPass) throws BankException;

	public UserBean authenticUserId(UserBean checkUserId) throws BankException;

	public UserBean UserDetails_add(UserBean userBean, long existingAccId,
			long accId) throws BankException;

	public List<UserBean> getMultipleAccounts(UserBean loginCredentials) throws BankException;
	
	public boolean checkTransactionPwd(long account_Id,String tranPwd) throws BankException;

}
