package com.capgemini.obs.service;

import com.capgemini.obs.bean.FundTransferBean;
import com.capgemini.obs.bean.PayeeBean;
import com.capgemini.obs.dao.IPayeeDAO;
import com.capgemini.obs.dao.PayeeDAO;
import com.capgemini.obs.exceptions.BankException;

public class PayeeService implements IPayeeService {
	
	IPayeeDAO payeeDao = null;
	PayeeBean payee = null;
	FundTransferBean fundTransferBean = null;
	/*******************************************************************************************************
	 - Function Name	:	checkPayee(PayeeBean payeeBean)
	 - Input Parameters	:	PayeeBean payeeBean
	 - Return Type		:	int
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	11/11/2016
	 - Description		:	Calls dao method checkPayee(PayeeBean payeeBean)
	 ********************************************************************************************************/
	@Override
	public int checkPayee(PayeeBean payeeBean) throws BankException {
		payeeDao = new PayeeDAO();
		int result = payeeDao.checkPayee(payeeBean);
		return result;
	}
	
	/*******************************************************************************************************
	 - Function Name	:	addPayee(PayeeBean payeeBean) 
	 - Input Parameters	:	PayeeBean payeeBean
	 - Return Type		:	PayeeBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	11/11/2016
	 - Description		:	Calls dao method addPayee(PayeeBean payeeBean) 
	 ********************************************************************************************************/
	@Override
	public PayeeBean addPayee(PayeeBean payeeBean) throws BankException {
		payeeDao = new PayeeDAO();
		payee = payeeDao.addPayee(payeeBean);
		return payee;
	}
	
	/*******************************************************************************************************
	 - Function Name	:	payPayee(FundTransferBean fundTransfer)
	 - Input Parameters	:	FundTransferBean fundTransfer
	 - Return Type		:	FundTransferBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	11/11/2016
	 - Description		:	Calls dao method payPayee(FundTransferBean fundTransfer)
	 ********************************************************************************************************/
	@Override
	public FundTransferBean payPayee(FundTransferBean fundTransfer) throws BankException {
		payeeDao = new PayeeDAO();
		fundTransferBean = payeeDao.payPayee(fundTransfer);
		return fundTransferBean;
	}

}
