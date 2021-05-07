package com.projectzero;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.projectzero.model.Account;
import com.projectzero.service.AccountService;



public class AdminWelcomeScreen implements Screen{
 	static int menuInput;
 	static double depositAmount;
 	static double withdrawAmount;
 	static double transferAmount;
 	static int transferAccountNo;
 	int accountId;
 	boolean success;
 	boolean depositSuccess;
 	boolean withdrawSuccess;
 	double newBalance;
 	double newBalanceTo;
 	double newBalanceFrom;
 	int accountIdToTransferTo;
 	int accountIdToTransferFrom;
 	int approveDenyId;
 	int approveDenyChoice;
 	boolean accountExists;
 	boolean transferFromExists;
 	boolean transferToExists;
 	String accountStatus;
 	String fromAccountStatus;
 	String toAccountStatus;
 	List<Account> pendingAccountList = new ArrayList<>();
 	AccountService as = new AccountService();
 	
 	public final Logger log3 = Logger.getLogger(AdminWelcomeScreen.class);
	@Override
	public void render(Scanner conScan) {
		// TODO Auto-generated method stub
		
		while(true) {
			System.out.println();
			System.out.println("			 Our Bank"					);
			System.out.println();
			System.out.println("======================================");
			System.out.println("Welcome "+LoginScreen.U_name);
			System.out.println("Please select from the following menu");
			System.out.println("========================================");
			
			//show this menu if user has an account
			System.out.println("1. Deposit money");
			System.out.println("2. Withdraw money ");
			System.out.println("3. Transfer money");
			System.out.println("4. Approve/Deny account");
			System.out.println("5. Cancel Account");
			System.out.println("6. Display All Account Information");
			System.out.println("7. Logout");
			menuInput = InputValidation.getIntChoice(7);
			
			
			switch(menuInput) {
			case 1:
				System.out.println("");
				System.out.println("Deposit money");
				System.out.println("=======================================");	
				
				while(true) {
					System.out.println("Enter account id to deposit: ");
					accountId = InputValidation.getIntChoice(1000);
					accountExists = as.checkIfAccountExistsOrNot(accountId);
					if(accountExists == true) {
						accountStatus = as.checkAccountStatus(accountId);
						if(accountStatus.equals("active")) {
							System.out.println("Please enter amount to deposit: ");
							depositAmount = InputValidation.getDoubleInput();
							success = as.deposit(depositAmount, accountId);
							if(success) {
								System.out.println("deposit Successful.");
							}else {
								log3.error("Deposit by admin Unsuccessful");
							}
							newBalance = as.checkBalance(accountId);
							System.out.println("Updated balance in account "+accountId+ " is "+newBalance);	
						}
						break;
					}
					else {
						System.out.println("Error! This Account Id does not exist.");
						System.out.println();
					}
				}
				break;
			case 2:
				System.out.println("");
				System.out.println("Withdraw money");
				System.out.println("=======================================");
				
				
				while(true) {
					System.out.println("enter account id to withdraw: ");
					accountId = InputValidation.getIntChoice(1000);
					accountExists = as.checkIfAccountExistsOrNot(accountId);
					if(accountExists == true) {
						accountStatus = as.checkAccountStatus(accountId);
						if(accountStatus.equals("active")) {
							System.out.println("Please enter amount to withdraw: ");
							withdrawAmount = InputValidation.getDoubleInput();
							success = as.withdraw(withdrawAmount, accountId);
							if(success) {
								System.out.println("Withdraw Successful.");
								newBalance = as.checkBalance(accountId);
								System.out.println("Updated balance in account "+accountId+ " is "+newBalance);	
								break;
							}
							else {
								System.out.println("Withdraw Failed.");
							}
						}
					}
					else {
						System.out.println("Error! This Account Id does not exist.");
						System.out.println();
					}
				}
				break;
				
			case 3: 
				System.out.println("");
				System.out.println("Transfer money");
				System.out.println("=======================================");
				
				while(true) {
					System.out.println("Please enter the account number to transfer to: ");
					accountIdToTransferTo = InputValidation.getIntChoice(1000);
					transferToExists = as.checkIfAccountExistsOrNot(accountIdToTransferTo);
					accountStatus = as.checkAccountStatus(accountIdToTransferTo);
					if(transferToExists && accountStatus.equals("active")) {
						newBalanceTo = as.checkBalance(accountIdToTransferTo);
					}
					else if (!transferToExists) {
						System.out.println("This account does not exist. ");
						break;
					}
					else if(!accountStatus.equals("active")) {
						System.out.println("This account is not active.");
						break;
					}
					
					System.out.println("Please enter the account number to transfer from: ");
					accountIdToTransferFrom = InputValidation.getIntChoice(1000);
					transferFromExists = as.checkIfAccountExistsOrNot(accountIdToTransferFrom);
					accountStatus = as.checkAccountStatus(accountIdToTransferTo);
	
					if(transferFromExists  && accountStatus.equals("active")) {
						newBalanceFrom = as.checkBalance(accountIdToTransferFrom);
						System.out.println("Please enter amount to transfer: ");
						transferAmount = InputValidation.getDoubleInput();
						
						withdrawSuccess = as.withdraw(transferAmount, accountIdToTransferFrom);
						depositSuccess = as.deposit(transferAmount, accountIdToTransferTo);
						if(withdrawSuccess) {
							System.out.println("Transfer Successful.");
							newBalance = as.checkBalance(accountIdToTransferTo);
							System.out.println("Updated balance in account "+accountIdToTransferTo+ " is "+newBalance);	
							break;
						}
						else {
							System.out.println("Withdraw Failed.");
							break;
						}
					}
					else if (!transferToExists) {
						System.out.println("This account does not exist. ");
						break;
					}
					else if(!accountStatus.equals("active")) {
						System.out.println("This account is not active.");
						break;
					}
				}
				
				break;
				
			case 4:
				System.out.println("");
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
				

				accountExists = as.checkIfAccountExistsOrNot(approveDenyId);
				if(accountExists==true) {
					System.out.println("Enter 1 to Approve and 2 to deny: ");
					approveDenyChoice =  InputValidation.getIntChoice(2);
					as.approveDenyAccount(approveDenyId, approveDenyChoice);
					pendingAccountList = as.pendingAccountList();
					for(Account account:pendingAccountList) {
						System.out.println(account);
					}
				}
				else {
					System.out.println("Account does not exist.");
				}
				
				
//				System.out.println("Enter 1 to Approve and 2 to deny: ");
//				approveDenyChoice =  InputValidation.getIntChoice(2);
//				as.approveDenyAccount(approveDenyId, approveDenyChoice);
//				pendingAccountList = as.pendingAccountList();
//				for(Account account:pendingAccountList) {
//					System.out.println(account);
//				}
				break;
				
			case 5: 
				System.out.println("");
				System.out.println("Cancel account");
				System.out.println("=======================================");
				

				
				System.out.println("Please enter account id to cancel:  ");
				accountId=  InputValidation.getIntChoice(1000);
				accountExists = as.checkIfAccountExistsOrNot(accountId);
				if(accountExists==true) {
					success = as.deleteUserAndAccount(accountId) ;
					if(success==true) {
						System.out.println("Account successfully cancelled.");
					}
					else
						System.out.println("Account not found.");
						
					break;
				}
				else {
					log3.error("Account not found to cancel");//log4j
					System.out.println("Account does not exist.");
				}
				break;
				
			case 6: 
				System.out.println("");
				System.out.println("Display all account information");
				System.out.println("=======================================");
				
				//generate all account
				List<Account> allAccountList = as.viewAllAccounts();
				for(Account account:allAccountList) {
					System.out.println(account);
					System.out.println();
				}
				break;
			case 7:	
				System.out.println("");
				System.out.println("Logout ? y/n");
				String exitAdm = conScan.nextLine();
				
				if(exitAdm.toLowerCase().equals("y")) {
					new MenuScreen().render(conScan);
					
				}
				break;
			
			}
		}
	}
}
