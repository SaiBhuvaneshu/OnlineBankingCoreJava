package com.capgemini.obs.service;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.exceptions.BankException;

public interface ICustomerService {

	public CustomerBean addCustomerDetails(CustomerBean customerBean,
			long accountId) throws BankException;

	public CustomerBean viewAddressDetails(long AccountId) throws BankException;

	public CustomerBean viewEmailDetails(long AccountId) throws BankException;

	public int updateCustomerAddress(CustomerBean customerbean)
			throws BankException;

	public int updateEmail(CustomerBean customerbean) throws BankException;

	public CustomerBean fetchCustomerDetails_add(long existingAccId, long newAccId) throws BankException;

	public String getUserName(long accountId) throws BankException;

}
