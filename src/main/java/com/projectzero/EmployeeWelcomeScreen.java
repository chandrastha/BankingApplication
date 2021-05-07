package com.projectzero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.projectzero.model.Account;
import com.projectzero.model.Users;
import com.projectzero.service.AccountService;
import com.projectzero.service.UsersService;

public class EmployeeWelcomeScreen implements Screen {
 	static int menuInput;
 	static double depositAmount;
 	static double withdrawAmount;
 	static double transferAmount;
 	static int transferAccountNo;
 	int accountId;
 	boolean success;
 	double newBalance;
 	int accountIdToTransfer;
 	int approveDenyId;
 	int approveDenyChoice;
 	boolean accountExists;
 	int userId;
 	boolean userExists;
 	List<Account> pendingAccountList = new ArrayList<>();
 	AccountService as = new AccountService();
 	UsersService us = new UsersService();
	@Override
	public void render(Scanner conScan) {
		
		while(true) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("			 Our Bank"					);
		System.out.println();
		System.out.println("======================================");
		System.out.println("Welcome "+LoginScreen.U_name);
		System.out.println("Please select from the following menu");
		System.out.println("========================================");
		
		//show this menu if user has an account

		System.out.println("1. Check Account Information");
		System.out.println("2. Check Account Balance ");
		System.out.println("3. Check Personal Information");
		System.out.println("4. Approve/deny Account");
		System.out.println("5. Display All Account Information");
		System.out.println("6. Logout");
		menuInput = InputValidation.getIntChoice(6);
		
		
		switch(menuInput) {
		case 1:
			//display account info
			System.out.println();
			System.out.println("Check Account Information");
			System.out.println("=======================================");
			System.out.println("Please enter account id: ");
			accountId = InputValidation.getIntChoice(1000);
			accountExists = as.checkIfAccountExistsOrNot(accountId);
			if(accountExists == true) {
				List<Account> accountDetails = as.getAccountDetails(accountId);
				for(Account account:accountDetails) {
					System.out.println(account);
					System.out.println();
				}
			}
			else {
				System.out.println("Error! This Account Id does not exist.");
				System.out.println();
			}
			
			break;
		case 2:
			//check account balance
			System.out.println();
			System.out.println("Check Account Balance");
			System.out.println("=======================================");
			System.out.println("Please enter account id: ");
			accountId = InputValidation.getIntChoice(1000);
			accountExists = as.checkIfAccountExistsOrNot(accountId);
			if(accountExists == true) {
				double balance = as.checkBalance(accountId);
				System.out.println("Account balance is : " + balance);
			}
			else {
				System.out.println("Error! This Account Id does not exist.");
				System.out.println();
			}
			break;
			
		case 3: 
			
			//display user info
			System.out.println();
			System.out.println("Check Personal Information");
			System.out.println("=======================================");
			System.out.println("Please enter user id: ");
			userId = InputValidation.getIntChoice(1000);
			userExists = us.checkIfUserExistsOrNot(userId);
			if(userExists == true) {
				Users userDetails = us.getUsersDetails(userId);
				System.out.println(userDetails);
				
			}
			else {
				System.out.println("Error! This Account Id does not exist.");
				System.out.println();
			}
			break;
		case 4:
			System.out.println();
			System.out.println("Approve/Deny account");
			System.out.println("=======================================");
			System.out.println("List of pending accounts: ");
//			generate inactive account list
			pendingAccountList = as.pendingAccountList();
			for(Account account:pendingAccountList) {
				System.out.println(account);
			}
			
			System.out.println("Please enter account id to approve/deny:  ");
			approveDenyId = InputValidation.getIntChoice(1000);
			System.out.println("Enter 1 to Approve and 2 to deny: ");
			approveDenyChoice =  InputValidation.getIntChoice(2);
			as.approveDenyAccount(approveDenyId, approveDenyChoice);
			pendingAccountList = as.pendingAccountList();
			for(Account account:pendingAccountList) {
				System.out.println(account);
			}
			break;
			
		case 5:
			System.out.println();
			System.out.println("Display all account information");
			System.out.println("=======================================");
			
			//generate all account
			List<Account> allAccountList = as.viewAllAccounts();
			for(Account account:allAccountList) {
				System.out.println(account);
			}
			break;
		case 6:	
			System.out.println("");
			System.out.println("Logout ? y/n");
			String exitEmp = conScan.nextLine();
			
			if(exitEmp.toLowerCase().equals("y")) {
				new MenuScreen().render(conScan);
			}
			break;
		}
	}
	}
}
