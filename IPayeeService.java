package com.capgemini.obs.service;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.exceptions.BankException;

public interface IPayeeService {
	
	public int checkPayee(PayeeBean payeeBean) throws BankException;
	
	public PayeeBean addPayee(PayeeBean payeeBean) throws BankException;
	
	public FundTransferBean payPayee(FundTransferBean fundTransfer) throws BankException;
}
