package com.capgemini.obs.dao;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.exceptions.BankException;

public interface ICustomerDAO {

	public CustomerBean addCustomerDetails(CustomerBean customerbean, long accountId)
			throws BankException;
	
	public CustomerBean viewAddressDetails(Long accountId) throws BankException;

	public CustomerBean viewEmailDetails(long accountId) throws BankException;

	public int updateCustomerAddress(CustomerBean customerbean)
			throws BankException;

	public int updateEmail(CustomerBean customerbean) throws BankException;

	public CustomerBean fetchCustomerDetails_add(long ExistingAccId, long newAccId) throws BankException;

	public String getUserName(long accountId) throws BankException;

	
}
