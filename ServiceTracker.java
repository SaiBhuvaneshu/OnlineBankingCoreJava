package com.capgemini.obs.service;

import java.util.List;

import com.capgemini.obs.bean.ServiceTrackerBean;
import com.capgemini.obs.dao.ServiceTrackerDAO;
import com.capgemini.obs.exceptions.BankException;

public class ServiceTracker implements IServiceTracker{

	
	
	/*******************************************************************************************************
	 - Function Name	:	trackServiceRequest
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	ServiceTrackerBean
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	view service request of an account from database calls dao method trackServiceRequest(ServiceTrackerBean serviceTracker) 
	 ********************************************************************************************************/
	@Override
	public ServiceTrackerBean trackServiceRequest(ServiceTrackerBean serviceTracker) throws BankException {
        
		validateServiceRequestNumber(serviceTracker);
		ServiceTrackerDAO serviceTrackerDAO = new ServiceTrackerDAO();
		return serviceTrackerDAO.trackServiceRequest(serviceTracker);
	}
    
	public void validateServiceRequestNumber(ServiceTrackerBean serviceTracker) throws BankException
	{
		if(serviceTracker.getServiceId()<=0)
		{
			throw new BankException("Please provide valid Service Request Number");
		}
	}

	
	/*******************************************************************************************************
	 - Function Name	:	trackAllRequest
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	List
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	view list of service requests of an account from database calls dao method trackAllRequest(ServiceTrackerBean serviceTracker) 
	 ********************************************************************************************************/
	@Override
	public List<ServiceTrackerBean> trackAllRequest(ServiceTrackerBean serviceTracker)
			throws BankException {
      
		ServiceTrackerDAO serviceTrackerDAO = new ServiceTrackerDAO();
		return serviceTrackerDAO.trackAllRequest(serviceTracker);
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	reqChequeBook
	 - Input Parameters	:	ServiceTrackerBean object
	 - Return Type		:	int
	 - Throws			:  	BankException
	 - Author			:	CAPGEMINI
	 - Creation Date	:	29/09/2018
	 - Description		:	gives service id for request from database calls dao method reqChequeBook(ServiceTrackerBean serviceTracker)
	 ********************************************************************************************************/
	@Override
	public int reqChequeBook(ServiceTrackerBean serviceTracker) throws BankException{
		
		ServiceTrackerDAO reqChequeBookDAO = new ServiceTrackerDAO();
		validateServiceDescription(serviceTracker.getServiceDescription());
		return reqChequeBookDAO.reqChequeBook(serviceTracker);
	}
    
	public void validateServiceDescription(String description) throws BankException
	{
		if(description.length()<=2)
		{
			 throw new BankException("Service Description should be atleast 3 letters long");
		}
	}
}
