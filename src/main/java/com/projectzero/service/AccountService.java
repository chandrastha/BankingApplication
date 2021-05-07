package com.projectzero.service;

import java.util.ArrayList;
import java.util.List;

import com.projectzero.dao.AccountDAOImpl;
import com.projectzero.dao.UsersDAOImpl;
import com.projectzero.model.Account;
import com.projectzero.model.Users;

public class AccountService {
	
	private AccountDAOImpl aDao = new AccountDAOImpl();
	private UsersDAOImpl uDao = new UsersDAOImpl();
	
	public AccountService() {
		
	}
	
	public AccountService(AccountDAOImpl aDao) {
		this.aDao = aDao;
	}
	
	public boolean verifyUser(String username, String password) {
		return false;
		
	}
	
	
	//deposit into account
	public boolean deposit(double amount,int accountId ) {
		boolean depositSuccess = false;

		
		depositSuccess = aDao.deposit(amount, accountId);
		return depositSuccess;
		
	}
	
	//withdraw from account
	public boolean withdraw(double amount,int accountId ) {
		boolean withdrawSuccess = false;
		double availableBalance = aDao.checkBalance(accountId);
		if(availableBalance>=amount) {
			withdrawSuccess = aDao.withdraw(amount, accountId);
		}
		else {
			System.out.println("Insufficient Balance.");
			System.out.println("Please enter amount less than or equal to "+availableBalance);
		}
		return withdrawSuccess;
		
	}
	
	
	//transfer from one account to another
	public boolean transfer(double amount, int fromAccount, int toAccount) {
		boolean transferSuccess = false;
		transferSuccess = aDao.transfer(amount, fromAccount, toAccount);
		return transferSuccess;
	}
	
	//get account id from username and password
	public int getAccountId(String username, String password) {
		
		int accountId = 0;
		try {		
			if(username!=null && password!=null) {
				Users user = uDao.getUser(username,password);
				int userId = user.getUserId();
				accountId = aDao.getAccountId(userId);
		}}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		return accountId;
	}
	
	//check balance
	
	public double checkBalance(int accountId) {
		double balance = 0;
		balance = aDao.checkBalance(accountId);
		return balance;
	}
	
	//get list of accounts with inactive status
	public List<Account> pendingAccountList(){
		List<Account> pendingList= new ArrayList<>();
		
		pendingList = aDao.getInactiveAccounts();
		return pendingList;
	}
	
	//approve or deny account
public void approveDenyAccount(int approveDenyId, int approveDenyChoice) {
		int isApprovedDenied = aDao.approveDenyAccount(approveDenyId, approveDenyChoice);
		if(isApprovedDenied==1) {
			System.out.println("Account is activated.");
		}
		else if (isApprovedDenied==-1) {
			System.out.println("Account is denied.");
		}
	}

//delete account
public boolean deleteAccount(int id) {
	boolean isDeleted= false;
	isDeleted = aDao.deleteAccount(id);
	return isDeleted;
	
}

//get all accounts
	public List<Account> viewAllAccounts() {
		List<Account> allAccount= new ArrayList<>();
		allAccount = aDao.viewAllAccounts();
		return allAccount;
		
	}
	
	
//	createNewAccount
	public int createNewAccount(String type) {
		int accountid = 0;
		accountid= aDao.createNewAccount(type);
		return accountid;
	}
	
//create new joint account
	public int createNewJointCustomerAccount() {
		int accountid = 0;
		accountid = aDao.createNewJointCustomerAccount();
		return accountid;
	}
	
//	create junction table
	public void createJunctionTable(int userid, int accountid) {
		aDao.createJunctionTable(userid,accountid);
	}
	

	
	//check account status using accountId
	public String checkAccountStatus(int accountId) {
		String accountStatus = null;
		
		try {
			accountStatus = aDao.checkStatus(accountId);
			if(accountStatus.equals("inactive")) {
				System.out.println("Account is not activated. Please wait for the Admin to activate the account.");
			}	}
			catch(NullPointerException e) {
				
			}
	
		return accountStatus;
	}
	
	//check account status using username and password
	public String checkAccountStatus(String username, String password) {
		String accountStatus = null;
		int accountId = 0;
		accountId = getAccountId(username,password);
		
		try {
			accountStatus = aDao.checkStatus(accountId);
			if(accountStatus.equals("inactive")) {
				System.out.println("Account is not activated. Please wait for the Admin to activate the account.");
			}	
		}
		catch(NullPointerException e) {
				
		}
		return accountStatus;
	}
	
//	check if account exists or not
	public boolean checkIfAccountExistsOrNot(int accountId) {
		boolean accountExists = false;
		try {
			accountExists = aDao.checkAccountExistOrNot(accountId);

			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}
		
		return accountExists;
		
	}
	
	//delete user and account both for user id (takes in account id)
	
	public boolean deleteUserAndAccount(int accountId) {
		boolean isDeleted = false;
		boolean userDeleted= false;
		boolean accountDeleted = false;
		

		//get user id from junction table
		 int userId=aDao.getUserIdFromJunctionTable(accountId);
		 
		 //check if the account is joined
		 boolean joined = aDao.checkIfAccountIsJoined(accountId) ;
		 if(joined==true) {
			 //get user ids if thereis there are multiple users id(for joint account)
			 List<Integer> userIdArray = aDao.getUsersIdFromJunctionTable(accountId);
			 

			 for(int userIds:userIdArray) {
				 userDeleted = uDao.deleteUser(userIds);
			 }
		 }
		 else {
				//delete user from user table
				userDeleted = uDao.deleteUser(userId);
		 }

		//get account id from account table
		accountDeleted = aDao.deleteAccount(accountId);
		
		if(userDeleted && accountDeleted) {
			isDeleted = true;
		}
		return isDeleted;
	}
	
	
	public List<Account> getAccountDetails(int accountId){
		List<Account> accountDetails = new ArrayList<>();
		accountDetails = aDao.getAccountInformation(accountId);
		
		return accountDetails;
		
		
	}
	
}
