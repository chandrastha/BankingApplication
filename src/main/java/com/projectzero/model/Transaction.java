package com.projectzero.model;

public class Transaction {
	
	private int transactionId;
	private String date;
	private double amount;
	private String type;
	
	public Transaction() {
		super();
	}
	
	public Transaction(String date,double amount, String type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
	
	public Transaction(int transactionId, String date,double amount, String type) {
		this.transactionId = transactionId;
		this.date = date;
		this.amount = amount;
		this.type = type;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", date=" + date + ", amount=" + amount + ", type="
				+ type + "]";
	}
	
}
