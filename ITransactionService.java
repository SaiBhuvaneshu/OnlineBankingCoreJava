package com.capgemini.obs.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.exceptions.BankException;

public interface ITransactionService {


	List<TransactionsBean> viewDailyTransactionDetails(LocalDate dateOfTransaction) throws BankException;

	List<TransactionsBean> viewMyTransactionDetails(LocalDate stdate,
			LocalDate endate) throws BankException ;

	public boolean updateTransaction(long account_ID, long payee_Account_Id, String tran_desc, int other_bank_amount) throws BankException;
}
