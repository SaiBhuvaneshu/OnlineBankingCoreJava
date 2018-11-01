package com.capgemini.obs.bean;

import java.util.Date;

public class ServiceTrackerBean {

	private int serviceId;
	private String serviceDescription;
	private long accountId;
	private Date serviceRaisedDate;
	private String serviceStatus;

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public Date getServiceRaisedDate() {
		return serviceRaisedDate;
	}

	public void setServiceRaisedDate(Date serviceRaisedDate) {
		this.serviceRaisedDate = serviceRaisedDate;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	@Override
	public String toString() {
		return "  " + serviceId
				+ "          " + serviceDescription + "         "
				 + serviceRaisedDate
				+ "           " + serviceStatus;
	}
	
	public void print(String format, int index) {
		System.out.format(format, index, serviceId, serviceDescription ,accountId, serviceRaisedDate, serviceStatus);
		}
}
