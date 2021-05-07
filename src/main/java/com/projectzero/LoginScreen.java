package com.projectzero;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.projectzero.service.AccountService;
import com.projectzero.service.UsersService;

public class LoginScreen implements Screen {
	
	public final Logger log2 = Logger.getLogger(LoginScreen.class);
	static String U_name;
	static String P_word;

	UsersService userService = new UsersService();
	AccountService accountService = new AccountService();

	@Override
	public void render(Scanner conScan) {

		System.out.println("			 Our Bank");
		System.out.println();
		System.out.println("Welcome to Our Bank");
		System.out.println();
		System.out.println("======================================");
		System.out.println("Please enter username:");
		U_name = InputValidation.getStringInput(30);
		System.out.println("Please enter password:");
		P_word = InputValidation.getStringInput(30);

		// method to check if user exists. Compare username and password in the database
		// and return whether user exists ir not.

		if (userService.login(U_name, P_word) == true) {

			System.out.println();
			int role = userService.getRole(U_name, P_word);
			switch (role) {

			case 1:
				new AdminWelcomeScreen().render(conScan);
			case 2:
				new EmployeeWelcomeScreen().render(conScan);
			case 3:
				if(accountService.checkAccountStatus(U_name, P_word).equals("active")) {
					new CustomerWelcomeScreen().render(conScan);
				}
				else
					new MenuScreen().render(conScan);
					break;
			}
		} 
		else {
			System.out.println("Error ! Username and Password doesn't match.");
			System.out.println("Please enter it again.");
			System.out.println();
			System.out.println();
			log2.error("Wrong Username Password Combination.");
			new LoginScreen().render(conScan);
		}

//		conScan.close();
	}

}
