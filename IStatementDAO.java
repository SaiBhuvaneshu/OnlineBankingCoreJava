package com.capgemini.obs.dao;

import java.util.List;

import com.capgemini.obs.bean.StatementBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.exceptions.BankException;

public interface IStatementDAO {

	public List<TransactionsBean> viewMiniStatement(StatementBean stmt) throws BankException;
	public List<TransactionsBean> viewDetailedStatement(StatementBean stmt) throws BankException;
}
