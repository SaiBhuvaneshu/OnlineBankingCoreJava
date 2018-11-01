package com.capgemini.obs.service;

import java.beans.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.obs.bean.StatementBean;
import com.capgemini.obs.bean.TransactionsBean;
import com.capgemini.obs.dao.StatementDAOImpl;
import com.capgemini.obs.exceptions.BankException;

public class StatementServiceImpl implements IStatementService {

	/*******************************************************************************************************
	 - Function Name	:	viewMiniStatement
	 - Input Parameters	:	StatementBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	19/09/2018
	 - Description		:	view mini statement of an account from database calls dao method viewMiniStatement( StatementBean) 
	 ********************************************************************************************************/
	@Override
	public List<TransactionsBean> viewMiniStatement(StatementBean stmt) throws BankException {
			
			StatementDAOImpl stmtDAO = new StatementDAOImpl();
			
			return stmtDAO.viewMiniStatement(stmt);
	}

	/*******************************************************************************************************
	 - Function Name	:	viewDetailedStatement
	 - Input Parameters	:	StatementBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	19/09/2018
	 - Description		:	view detailed statement of an account over a time period from database calls dao method viewDetailedStatement( StatementBean) 
	 ********************************************************************************************************/
	
	@Override
	public List<TransactionsBean> viewDetailedStatement(StatementBean stmt) throws BankException{
		
		StatementServiceImpl stmtService = new StatementServiceImpl();
		stmtService.isValidStartEndDate(stmt);
        StatementDAOImpl stmtDAO = new StatementDAOImpl();
		
		return stmtDAO.viewDetailedStatement(stmt);
	}
	
	public void isValidStartEndDate(StatementBean stmt) throws BankException
	{
		List<String> validationErrors = new ArrayList<String>();
		if(!isValidStartDate(stmt.getStartDate()))
		{
			validationErrors.add("Start Date should not exceed Today Date");
		}
		if(!isValidStartEndDate(stmt.getStartDate(), stmt.getEndDate()))
		{
			validationErrors.add("Start Date should not be greater than End Date");
		}
		if(!isValidEndDate(stmt.getEndDate()))
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
	public boolean isValidStartEndDate(LocalDate startDate,LocalDate endDate)
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
}
