package com.capgemini.obs.bean;

import java.time.LocalDate;

public class TransactionsBean {

	private int transactionId;
	private String tranDescription;
	private LocalDate dateOfTransaction;
	private String transactionType;
	private int tranAmount;
	private long accountId;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTranDescription() {
		return tranDescription;
	}

	public void setTranDescription(String tranDescription) {
		this.tranDescription = tranDescription;
	}

	public LocalDate getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(LocalDate dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(int tranAmount) {
		this.tranAmount = tranAmount;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return transactionId + ",              " + tranDescription
				+ ",            	      " + dateOfTransaction + ",            	"
				+ transactionType + ",         " + tranAmount;
	}
	
	public void print(String format, int index) {
		System.out.format(format, index, transactionId, tranDescription, dateOfTransaction, transactionType,
				tranAmount, accountId);
		}

}
