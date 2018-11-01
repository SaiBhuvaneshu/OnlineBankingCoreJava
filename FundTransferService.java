package com.capgemini.obs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.dao.FundTransferDAO;
import com.capgemini.obs.dao.IFundTransferDAO;
import com.capgemini.obs.exceptions.BankException;

public class FundTransferService implements IFundTransferService{

	IFundTransferDAO fundTransferDao = null;
	FundTransferBean fundTransferBean = null;
	PayeeBean payee = null;
	TransactionsBean transactionBean = null;
	
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
		fundTransferDao = new FundTransferDAO();
		fundTransferBean = fundTransferDao.TransferSameAccount(fundTransferBean);  
		return fundTransferBean;
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
		fundTransferDao = new FundTransferDAO();
		boolean checkLimit = fundTransferDao.checkMaxFunds(account_ID,other_bank_amount);
		return checkLimit;
	}

	/*******************************************************************************************************
	 * - Function Name : validateDetails(FundTransferBean bean) 
	 * - Input Parameters : FundTransferBean bean 
	 * - Return Type : void 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description :Validating Fund Transfer Details 
	 ********************************************************************************************************/
	public void validateDetails(FundTransferBean bean) throws BankException
	{
		List<String> validationErrors = new ArrayList<String>();
		
		//Validating Payee Account Id
		if(!(isValidAccountId(bean.getPayeeAccountId()))){
			validationErrors.add("\n PayeeAccountId should be in digits and should have minimum of 4 digits\n");
		}
		//Validating transfer Amount
		if(!(isValidAmount(bean.getTransferAmount()))){
			validationErrors.add("\n Amount Should be a positive Number \n" );
		}
		
		if(!validationErrors.isEmpty())
			throw new BankException(validationErrors +"");
	}

	private boolean isValidAccountId(long accId) {
		return (accId > 999 && accId <= 999999999 );
		
	}
	
	public boolean isValidAmount(int amount){
		return (amount>0);
	}
	
}
