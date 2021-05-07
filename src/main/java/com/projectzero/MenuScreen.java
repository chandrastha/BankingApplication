package com.projectzero;
import java.util.Scanner;

public class MenuScreen implements Screen {
	static int menuNo;
	
	@Override
	public void render(Scanner conScan) {
		System.out.println();
		System.out.println();
		System.out.println("			 Our Bank"					);
		System.out.println();
		System.out.println("Welcome to Our Bank");
		System.out.println();
		System.out.println("======================================");
		System.out.println("Please select from the following menu:");
		System.out.println("1. Login into existing account");
		System.out.println("2. Create new account");
		System.out.println("3. Exit this application");
		System.out.println("=======================================");
		menuNo = InputValidation.getIntChoice(3);
		
		switch(menuNo) {
		case 1:
			new LoginScreen().render(conScan);

		case 2:
			new CreateNewUserScreen().render(conScan);

		case 3:	
			System.out.println("Exit ? y/n");
			String exit = conScan.nextLine();
//			System.out.println(exit+" this is causing problem.");/
			if(exit.toLowerCase().equals("y")) {
				MainDriver.isRunning = false;
				return;
			}
			break;
		}

	}
	
}
