package com.capgemini.obs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.obs.bean.CustomerBean;
import com.capgemini.obs.bean.UserBean;
import com.capgemini.obs.dao.CustomerDAO;
import com.capgemini.obs.dao.ICustomerDAO;
import com.capgemini.obs.dao.IUserDAO;
import com.capgemini.obs.dao.UserDAO;
import com.capgemini.obs.exceptions.BankException;

public class CustomerService implements ICustomerService {

	ICustomerDAO CustomerDao;
	IUserDAO userDao;
	UserBean userBean;

	/*******************************************************************************************************
	 * - Function Name : addCustomerDetails(CustomerBean customerBean, long accountId) 
	 * - Input Parameters : CustomerBean customerBean, long accountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Adding Customer Details
	 ********************************************************************************************************/	
	public CustomerBean addCustomerDetails(CustomerBean customerBean, long accountId) {
		CustomerDao = new CustomerDAO();

		try {
			CustomerDao.addCustomerDetails(customerBean, accountId);

		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerBean;
	}


	/*******************************************************************************************************
	 * - Function Name : getUserName
	 * - Input Parameters : long accountId 
	 * - Return Type : String 
	 * - Throws : BankException 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 12/10/2018 
	 * - Description : gets user name from dao method getUserName(long accountId)
	 ********************************************************************************************************/
	public String getUserName(long accountId) throws BankException {
		ICustomerDAO customerDao = new CustomerDAO();
		return customerDao.getUserName( accountId);
	}
	
	/*******************************************************************************************************
	 * - Function Name : validateCustomer(CustomerBean customerBean) 
	 * - Input Parameters : CustomerBean customerBean
	 * - Return Type : boolean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Validating Customer Details
	 ********************************************************************************************************/
	public boolean validateCustomer(CustomerBean customerBean)
			throws BankException {
		List<String> validationErrors = new ArrayList<String>();

		// Validating customer name
		if (!(isValidName(customerBean.getCustomerName()))) {
			validationErrors
					.add("\nCustomer Name Should Be In Alphabets and minimum 3 characters long ! \n");
		}
		// Validating address
		if (!(isValidAddress(customerBean.getAddress()))) {
			validationErrors
					.add("\nAddress Should Be Greater Than 2 Characters \n");
		}
		// Validating email
		if (!(isValidEmail(customerBean.getEmail()))) {
			validationErrors.add("\nEmail should be valid \n");
		}
		// Validating pancard
		if (!(isValidPanCard(customerBean.getPanCard()))) {
			validationErrors.add("\n Pan card number should be valid \n");
		}

		if (!validationErrors.isEmpty()) {
			throw new BankException(validationErrors + "");
		} else
			return true;
	}

	public boolean isValidName(String customerName) {
		if(customerName.length() >= 3)
		{
		Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z ]*");
		Matcher nameMatcher = namePattern.matcher(customerName);
		return nameMatcher.matches();
		}
		else
		{
		return false;
		}
	}

	public boolean isValidAddress(String address) {
		return (address.length() > 2);
	}

	public boolean isValidEmail(String Email) {
		Pattern EmailPattern = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher EmailMatcher = EmailPattern.matcher(Email);
		return EmailMatcher.matches();

	}

	public boolean isValidPanCard(String PanCard) {
		Pattern PanCardPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		Matcher PanCardMatcher = PanCardPattern.matcher(PanCard);
		return PanCardMatcher.matches();
	}
	
	/*******************************************************************************************************
	 * - Function Name : updateCustomerAddress(CustomerBean customerbean) 
	 * - Input Parameters : CustomerBean customerbean
	 * - Return Type : int 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Updating Customer Address
	 ********************************************************************************************************/
	@Override
	public int updateCustomerAddress(CustomerBean customerbean) throws BankException {
		// TODO Auto-generated method stub
		CustomerDAO AddressUpdateDao;
		AddressUpdateDao = new CustomerDAO();
		int customerSeq;
		validateAddress(customerbean);
		customerSeq = AddressUpdateDao.updateCustomerAddress(customerbean);
		return customerSeq;
	}

	/*******************************************************************************************************
	 * - Function Name : updateEmail(CustomerBean customerbean) 
	 * - Input Parameters : CustomerBean customerbean
	 * - Return Type : int 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Updating Customer Email Address
	 ********************************************************************************************************/
	@Override
	public int updateEmail(CustomerBean customerbean) throws BankException {
		// TODO Auto-generated method stub
		CustomerDAO AddressUpdateDao;
		AddressUpdateDao = new CustomerDAO();
		int customerSeq;
		customerSeq = AddressUpdateDao.updateEmail(customerbean);
		return customerSeq;
	}

	/*******************************************************************************************************
	 * - Function Name : CustomerBean viewAddressDetails(Long AccountId) 
	 * - Input Parameters : Long AccountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Address Details
	 ********************************************************************************************************/
	public CustomerBean viewAddressDetails(long AccountId) throws BankException {
		CustomerDao = new CustomerDAO();
		CustomerBean bean = null;

		bean = CustomerDao.viewAddressDetails(AccountId);
		return bean;
	}
	
	public void validateAddress(CustomerBean customerbean) throws BankException {
		if (customerbean.getAddress().length() < 3) {
			throw new BankException(
					"\nAddress Should Be Greater Than 2 Characters \n");
		}
	}

	/*******************************************************************************************************
	 * - Function Name : CustomerBean viewEmailDetails(long AccountId) 
	 * - Input Parameters : long AccountId 
	 * - Return Type : CustomerBean 
	 * - Throws : {@link BankException} 
	 * - Author : CAPGEMINI 
	 * - Creation Date : 29/09/2018 
	 * - Description : Viewing Customer old Email Address
	 ********************************************************************************************************/
	@Override
	public CustomerBean viewEmailDetails(long AccountId) throws BankException {
		// TODO Auto-generated method stub
		CustomerDao = new CustomerDAO();
		CustomerBean bean = null;

		bean = CustomerDao.viewEmailDetails(AccountId);
		return bean;
	}

	public void validateEmail(CustomerBean customerbean) throws BankException {
		// TODO Auto-generated method stub
		List<String> validationError = new ArrayList<String>();
		if (!(isValidEmail(customerbean.getEmail()))) {
			validationError.add("\nEmail should be valid \n");
		}
		if (!validationError.isEmpty())
			throw new BankException(validationError + "");
	}

	@Override
	public CustomerBean fetchCustomerDetails_add(long existingAccId,long newAccId) {
		CustomerDao = new CustomerDAO();
		CustomerBean customerBean = new CustomerBean();
		
		try {
			customerBean = CustomerDao.fetchCustomerDetails_add(existingAccId, newAccId);
		} catch (BankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerBean;
	}
}
