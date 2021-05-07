package com.projectzero;

import java.util.Scanner;

import com.projectzero.service.AccountService;
import com.projectzero.service.UsersService;

public class CreateNewUserScreen implements Screen{
	String uName;
	String pWord;
	String uType;
	int choice;
	String uNameJoined;
	String pWordJoined;

	UsersService us = new UsersService();
	AccountService as  = new AccountService();
	
	@Override
	public void render(Scanner conScan) {
		
		System.out.println("			 Our Bank"					);
		System.out.println();
		System.out.println("======================================");
		System.out.println("Create new user");
		System.out.println("====================");
		System.out.println("Please enter username: ");
		uName = InputValidation.getStringInput(30);
		System.out.println("Please enter password: ");
		pWord = InputValidation.getStringInput(30);
		System.out.println("Please enter role: Customer or Employee or Admin");
		uType = InputValidation.getUsertypeInput();
		us.addUser(uName,pWord,uType) ;
		int userid = us.getUserID(uName, pWord);
		int usertype = us.getRole(uName, pWord);
		String role = null;
		if(usertype==1) {
			role = "admin";
		}else if(usertype ==2){
			role = "employee";
		}else if (usertype == 3){
			role = "customer";
			System.out.println("Would you like to create a joint account?");
			System.out.println("Press 1 for single and 2 for Joint account: ");
			choice = InputValidation.getIntChoice(2);
			if(choice == 2) {
				int accountId = as.createNewJointCustomerAccount();
				as.createJunctionTable(userid, accountId);
				
				System.out.println("Please enter username: ");
				uNameJoined = InputValidation.getStringInput(30);
				System.out.println("Please enter password: ");
				pWordJoined = InputValidation.getStringInput(30);
				us.addUser(uNameJoined,pWordJoined,"customer") ;
				int useridJoined = us.getUserID(uNameJoined, pWordJoined);
				as.createJunctionTable(useridJoined, accountId);
				System.out.println("Please Wait for the account activation.");
				new MenuScreen().render(conScan);
			}
			
		}
		
		int accountId = as.createNewAccount(role);
		as.createJunctionTable(userid, accountId);
		
		
		System.out.println("Please Wait for the account activation.");
		new MenuScreen().render(conScan);
		
	}

}
