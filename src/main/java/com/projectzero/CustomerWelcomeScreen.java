package com.projectzero;

import java.util.Scanner;

import com.projectzero.service.AccountService;

public class CustomerWelcomeScreen implements Screen{
	 	static int menuInput;
	 	static double depositAmount;
	 	static double withdrawAmount;
	 	static double transferAmount;
	 	static int transferAccountNo;
	 	int accountId;
	 	boolean success;
	 	double newBalance;
	 	int accountIdToTransfer;
	 	boolean transferToExists;
	 	double balance;
	 	
	 	AccountService as = new AccountService();
	@Override
	public void render(Scanner conScan) {
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
			System.out.println("4. Logout");
			menuInput = InputValidation.getIntChoice(4);
			
	
			switch(menuInput) {
			case 1:
				System.out.println("");
				System.out.println("Deposit money");
				System.out.println("=======================================");
				System.out.println("Please enter amount to deposit: ");
				depositAmount = InputValidation.getDoubleInput();
				accountId = as.getAccountId(LoginScreen.U_name,LoginScreen.P_word);
				success = as.deposit(depositAmount, accountId);
				
				if(success) {
					System.out.println("deposit Successful.");
				}
				newBalance = as.checkBalance(accountId);
				System.out.println("Your new Balance is "+newBalance);		
				break;
			case 2:
				System.out.println("");
				System.out.println("Withdraw money");
				System.out.println("=======================================");
				System.out.println("Please enter amount to withdraw: ");
				withdrawAmount = InputValidation.getDoubleInput();
				
				accountId = as.getAccountId(LoginScreen.U_name,LoginScreen.P_word);
				
				if(withdrawAmount<=as.checkBalance(accountId)) {
					success = as.withdraw(withdrawAmount, accountId);
					
					if(success) {
						System.out.println("Withdraw Successful.");
						newBalance = as.checkBalance(accountId);
						System.out.println("Your new Balance is "+ newBalance);		
						
					}


					
				}				
				else {
					System.out.println("Error ! Insufficient balance.");	
					}

				break;
			case 3: 
				System.out.println("");
				System.out.println("Transfer money");
				System.out.println("=======================================");
				System.out.println("Please enter amount to transfer: ");

				transferAmount = InputValidation.getDoubleInput();
				accountId = as.getAccountId(LoginScreen.U_name,LoginScreen.P_word);
				if(transferAmount <= as.checkBalance(accountId)) {
					System.out.println("Please enter the account id to transfer to:");
					accountIdToTransfer = InputValidation.getIntChoice(100000);
					transferToExists = as.checkIfAccountExistsOrNot(accountIdToTransfer);

					if(transferToExists ) {
						success = as.transfer(transferAmount,  accountId, accountIdToTransfer);
						newBalance = as.checkBalance(accountId);
						System.out.println("Transfer Successful. Your new Balance is "+newBalance);	
					}else {
						System.out.println("The account you want to transfer to does not exist.");
					}
				}
	
				else {
					System.out.println("Insufficient Balance!");
				}

				break;
			case 4:	
				System.out.println("");
				System.out.println("Logout ? y/n");
				String exitCus = conScan.nextLine();
				
				if(exitCus.toLowerCase().equals("y")) {
					new MenuScreen().render(conScan);
					break;
	
				}
				break;
			}
		}
	}
}

//show this if user doesn't have an account
//System.out.println("1. Apply for single account");
//System.out.println("2. Apply for joint account");