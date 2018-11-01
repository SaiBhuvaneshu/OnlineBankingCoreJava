package com.capgemini.obs.bean;

public class CustomerBean {

	private long accountId;
	private String customerName;
	private String email;
	private String address;
	private String panCard;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	@Override
	public String toString() {
		return "CustomerBean [accountId=" + accountId + ", customerName="
				+ customerName + ", email=" + email + ", address=" + address
				+ ", panCard=" + panCard + "]";
	}
	
	
}
