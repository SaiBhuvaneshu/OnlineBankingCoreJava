package com.capgemini.obs.bean;

public class UserBean {

	private long accountId;
	private long userId;
	private String loginPassword;
	private String secretQuestion;
	private String secretAnswer;

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	private String transactionPassword;
	private String lockStatus;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long l) {
		this.userId = l;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Override
	public String toString() {
		return "UserBean [accountId=" + accountId + ", userId=" + userId
				+ ", loginPassword=" + loginPassword + ", secretQuestion="
				+ secretQuestion + ", secretAnswer=" + secretAnswer
				+ ", transactionPassword=" + transactionPassword
				+ ", lockStatus=" + lockStatus + "]";
	}

}
