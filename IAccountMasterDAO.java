package com.capgemini.obs.dao;

import com.capgemini.obs.bean.AccountMasterBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;

public interface IAccountMasterDAO {

	public long addAccountDetails(AccountMasterBean accountMasterBean)
			throws BankException;
	
	public boolean checkAccountBalance(long account_ID, int other_bank_amount) throws BankException;
	
	public boolean checkAccountExists(long accountId) throws BankException;

}
