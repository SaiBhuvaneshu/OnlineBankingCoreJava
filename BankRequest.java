package com.capgemini.obs.bean;

import java.sql.Date;

public class BankRequest {

	// Account Master
	private int accountId;
	private String accountType;
	private int accountBalance;
	private Date opendate;

	// Customer
	// private int accountId;
	private String customerName;
	private String email;
	private String address;
	private String panCard;

	// Transactions
	private int transactionId;
	private String tranDescription;
	private Date DateOfTransaction;
	private String transactionType;
	private int tranAmount;
	//private int accountId;

	// Service Tracker
	private int serviceId;
	private String serviceDescription;
	// private int accountId;
	private Date serviceRaisedDate;
	private String serviceStatus;

	// User Table
	// private int accountId;
	private int userId;
	private String loginPassword;
	private String secretQuestion;
	private String transactionPassword;
	private String lockStatus;

	// Fund Transfer
	private int fundTransferId;
	// private int accountId;
	private Date dateOfTransfer;
	private int transferAmount;

	// Payee Table
	// private int accountId;
	private int payeeAccountId;
	private String nickName;

}
