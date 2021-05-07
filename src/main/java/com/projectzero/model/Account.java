package com.projectzero.model;

public class Account {
	
	private int accountId;
	private double balance;
	private String status;
	private String type;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Account(double balance, String status, String type) {
		super();
		this.balance = balance;
		this.status = status;
		this.type = type;
	}



	public Account(int accountId, double balance, String status, String type) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.status = status;
		this.type = type;
	}



	public int getAccountId() {
		return accountId;
	}



	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}



	public double getBalance() {
		return balance;
	}



	public void setBalance(double balance) {
		this.balance = balance;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + ", type=" + type
				+ "]";
	}
	
}
