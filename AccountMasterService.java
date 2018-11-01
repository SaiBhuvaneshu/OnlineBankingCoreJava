package com.capgemini.obs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.obs.bean.AccountMasterBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.dao.AccountMasterDAO;
import com.capgemini.obs.dao.CustomerDAO;
import com.capgemini.obs.dao.IAccountMasterDAO;
import com.capgemini.obs.dao.ICustomerDAO;
import com.capgemini.obs.dao.IUserDAO;
import com.capgemini.obs.dao.UserDAO;
import com.capgemini.obs.exceptions.BankException;

public class AccountMasterService implements IAccountMasterService{

	IAccountMasterDAO accountMasterDao;
	
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
	public long addAccountDetails(AccountMasterBean accountMasterBean) throws BankException {
		
		long accId=0;		
		accountMasterDao = new AccountMasterDAO();

		try {
			accId = accountMasterDao.addAccountDetails(accountMasterBean);

		} catch (BankException e) {
			
			e.printStackTrace();
		}		
		
		return accId;
	}

	/*******************************************************************************************************
	 * - Function Name : validateAccount(AccountMasterBean accountMasterBean) 
	 * - Input Parameters : AccountMasterBean accountMasterBean 
	 * - Return Type : boolean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : validating Account
	 ********************************************************************************************************/
	public boolean validateAccount(AccountMasterBean accountMasterBean)  throws BankException {
		
		List<String> validationErrors = new ArrayList<String>();

		// Validating Opening Balance
		if (!(isValidAccountBalance(accountMasterBean.getAccountBalance()))) {
			validationErrors
					.add("\n Please keep a minimum balance of Rs.2000 \n");
		}

		if (!validationErrors.isEmpty()) {
			throw new BankException(validationErrors + "");
		} else
			return true;
		}
		
		public boolean isValidAccountBalance(int i) {
			return (i >= 2000);
		}

		/*******************************************************************************************************
		 - Function Name	:	checkAccountExists(long accountId)
		 - Input Parameters	:	long accountId
		 - Return Type		:	boolean
		 - Author			:	CAPGEMINI
		 - Creation Date	:	07/10/2018
		 - Description		:	Calls dao method checkAccountExists(long accountId)
		 ********************************************************************************************************/
		@Override
		public boolean checkAccountExists(long accountId) throws BankException {
			
			accountMasterDao = new AccountMasterDAO();
			boolean checkAccount = accountMasterDao.checkAccountExists(accountId);
			return checkAccount;
		}
		
		/*******************************************************************************************************
		 - Function Name	:	checkAccountBalance(long account_ID, int other_bank_amount)
		 - Input Parameters	:	long account_ID, int other_bank_amount
		 - Return Type		:	boolean
		 - Author			:	CAPGEMINI
		 - Creation Date	:	07/10/2018
		 - Description		:	Calls dao method checkAccountBalance(long account_ID, int other_bank_amount)
		 ********************************************************************************************************/
		@Override
		public boolean checkAccountBalance(long account_ID, int other_bank_amount) throws BankException {
			accountMasterDao = new AccountMasterDAO();
			boolean checkBal = accountMasterDao.checkAccountBalance(account_ID,other_bank_amount);
			return checkBal;
		}
}
