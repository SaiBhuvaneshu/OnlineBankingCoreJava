package com.capgemini.obs.service;

import java.util.List;

import com.capgemini.obs.bean.ServiceTrackerBean;
import com.capgemini.obs.exceptions.BankException;

public interface IServiceTracker {

    public ServiceTrackerBean trackServiceRequest(ServiceTrackerBean serviceTracker) throws BankException;
	
	public List<ServiceTrackerBean> trackAllRequest(ServiceTrackerBean serviceTracker) throws BankException;
	
	public int reqChequeBook(ServiceTrackerBean serviceTracker) throws BankException;
	
}
