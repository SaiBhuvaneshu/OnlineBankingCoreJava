package com.capgemini.obs.bean;

import java.util.Date;

public class FundTransferBean {

	private int fundTransferId;
	private long accountId;
	private long payeeAccountId;
	private Date dateOfTransfer;
	private int transferAmount;

	public FundTransferBean() {
	
	}
	
	public int getFundTransferId() {
		return fundTransferId;
	}

	public void setFundTransferId(int fundTransferId) {
		this.fundTransferId = fundTransferId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getPayeeAccountId() {
		return payeeAccountId;
	}

	public void setPayeeAccountId(long payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}

	public Date getDateOfTransfer() {
		return dateOfTransfer;
	}

	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}

	public int getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(int transferAmount) {
		this.transferAmount = transferAmount;
	}

	public FundTransferBean(long accountId, long payeeAccountId,
			int transferAmount) {
		super();
		this.accountId = accountId;
		this.payeeAccountId = payeeAccountId;
		this.transferAmount = transferAmount;
	}
}
