package com.capgemini.obs.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.dao.ITransactionDAO;
import com.capgemini.obs.dao.TransactionDAO;
import com.capgemini.obs.exceptions.BankException;

public class TransactionService implements ITransactionService {

	ITransactionDAO transactiondao;

	
	/*******************************************************************************************************
	 * - Function Name : viewDailyTransactionDetails(LocalDate dateOfTransaction) 
	 * - Input Parameters : LocalDate dateOfTransaction
	 * - Return Type : List 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing daily Transaction
	 ********************************************************************************************************/
	@Override
	public List<TransactionsBean> viewDailyTransactionDetails(
			LocalDate dateOfTransaction) throws BankException {
		boolean isValidDate=false;

			isValidDate=isValidStartDate(dateOfTransaction);
			
			if(!isValidDate){
				throw new BankException("\nDate should not exceed today's date.");
			}
		transactiondao = new TransactionDAO();
		List<TransactionsBean> bean = transactiondao.viewDailyTransactionDetails(dateOfTransaction);

		return bean;
	}

	/*******************************************************************************************************
	 * - Function Name : viewMyTransactionDetails(LocalDate stdate, LocalDate endate) 
	 * - Input Parameters : LocalDate stdate, LocalDate endate
	 * - Return Type : List 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Monthly and yearly Transaction
	 ********************************************************************************************************/
	@Override
	public List<TransactionsBean> viewMyTransactionDetails(LocalDate stdate,
			LocalDate endate) throws BankException {
		transactiondao = new TransactionDAO();
		List<TransactionsBean> bean = null;
		isValidStartEndDate( stdate, endate) ;
		bean = transactiondao.viewMyTransactionDetails(stdate,endate);
		return bean;
	}
	
	public void isValidStartEndDate(LocalDate stdate,
			LocalDate endate) throws BankException
	{
		List<String> validationErrors = new ArrayList<String>();
		if(!isValidStartDate(stdate))
		{
			validationErrors.add("Start Date should not exceed Today Date");
		}
		if(!isValidStartAndEndDate(stdate,endate))
		{
			validationErrors.add("Start Date should not be greater than End Date");
		}
		if(!isValidEndDate(endate))
		{
			validationErrors.add("End Date should not exceed Today Date");
		}
		
		if(!validationErrors.isEmpty())
		{
            throw new BankException(validationErrors+"");
		}
	}
	
	public boolean isValidStartDate(LocalDate startDate)
	{
		LocalDate today = LocalDate.now();
    	if(startDate.compareTo(today)>0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
  }
	
	public boolean isValidStartAndEndDate(LocalDate startDate,LocalDate endDate)
	{
		if(startDate.compareTo(endDate)>0)
		    return false;
		else
			return true;
	}
    public boolean isValidEndDate(LocalDate endDate)
    {
    	LocalDate today = LocalDate.now();
    	if(endDate.compareTo(today)>0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    	
    }
    
    /*******************************************************************************************************
	 - Function Name	:	updateTransaction(long account_ID, long payee_Account_Id, 
	 							String tran_desc, int other_bank_amount)
	 - Input Parameters	:	long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount
	 - Return Type		:	boolean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	11/11/2016
	 - Description		:	Calls dao method updateTransaction(long account_ID, long payee_Account_Id, 
	 							String tran_desc, int other_bank_amount)
	 ********************************************************************************************************/
	@Override
	public boolean updateTransaction(long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount)
			throws BankException {
		transactiondao = new TransactionDAO();
		boolean update = transactiondao.updateTransaction(account_ID,payee_Account_Id,tran_desc,other_bank_amount);
		return update;
	}
}
