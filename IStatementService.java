package com.capgemini.obs.service;

import java.util.List;

import com.capgemini.obs.bean.StatementBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.exceptions.BankException;

public interface IStatementService {
	
	public List<TransactionsBean> viewMiniStatement(StatementBean stmt) throws BankException;
	public List<TransactionsBean> viewDetailedStatement(StatementBean stmt) throws BankException;

}
