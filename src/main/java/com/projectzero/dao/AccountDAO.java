package com.projectzero.dao;

import com.projectzero.model.Account;

public interface AccountDAO {
	
	//check balance
	double checkBalance(int accountId);

	//deposit
	boolean deposit( double amount, int accountId);
	
	//withdraw
	boolean withdraw(double amount, int accountId);
	
	//transfer
	boolean transfer(double amount, int fromAccountId, int toAccountId);
	
	//check status
	String checkStatus(int accountId);
	
	//insert into account
	public Account createAccount(double balance, String status, String type );
	
	//delete account
	public boolean deleteAccount(int accountId);
	
	//add user to account for joint account
	
	public int createNewAccount(String type );
}
