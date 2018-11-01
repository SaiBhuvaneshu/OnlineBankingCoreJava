package com.capgemini.obs.dao;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.exceptions.BankException;

public interface IFundTransferDAO {

	public FundTransferBean TransferSameAccount(FundTransferBean fundTransfer) throws BankException;
	
	public boolean checkMaxFunds(long account_ID, int other_bank_amount) throws BankException;
	
}
