package com.projectzero;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class InputValidation {
	
	private static Scanner scanner = new Scanner(System.in);
 	public final static Logger log4 = Logger.getLogger(InputValidation.class);
	
	//check if integer value
	public static int getIntChoice(int max) {
		int inputValue;
		
		do {
			while(!scanner.hasNextInt()) {
				scanner.nextLine();
				System.out.println("Error: Please enter a valid input");
			}
			
			//retrieve user inout
			inputValue = scanner.nextInt();
			scanner.nextLine();
			
			//confirm its within range
			if(inputValue<0 || inputValue>max) {
				log4.error("Entered value not in range.");//log4j
				System.out.println();
				System.out.println("Error: Please enter a number between 1 and "+max);
			}
		}while(inputValue<0 || inputValue>max);
		
		return inputValue;
	}
	
	
	// check the double input
	public static double getDoubleInput() {
		double inputValue;
		
		// Confirm user input is double type
		do {
			while(!scanner.hasNextDouble()) {
				scanner.nextLine();
				log4.error("Entered value not a number");//log4j
				System.out.println();
				System.out.println("Error: Please enter a number.");

			}
			
			// Retrieve user input
			inputValue = scanner.nextDouble();
			scanner.nextLine();
			
			// Confirm user input is within the range of 0 to max
		if(inputValue <= 0 ) {
			log4.error("Entered value less than 0");//log4j
			System.out.println();
			System.out.println("Error: Please enter a number more than 0: ");
			}
			
		}while(inputValue <= 0 );

		// Return user input
		return inputValue;
	}
	
	
	//check the string input
	public static String getStringInput(int max) {
		String input;
		
		while(true) {
			input = scanner.nextLine();
			
			input = input.trim();
			if(input.length() == 0){
				log4.error("Empty input.");//log4j
				System.out.println();
				System.out.println("Error: Input has no content");

				continue;
			}
			
			if(input.length() > max){
				System.out.println("Error: Enter string less than " + max + "characters");
				continue;
			}
			
			return input;
		}
	}
	
	//check the string input
	public static String getUsertypeInput() {
		String input;
		boolean flag =false;
		do {
			input = scanner.nextLine();
			
//			input = input.trim();
			input = input.toLowerCase();
//			if(input.length() == 0){
//				System.out.println("Error: String has no content");
//				continue;
//			}
			if(input.equals("admin") || input.equals("customer") || input.equals("employee") ) {
				flag = true;
			}
			else {
				log4.error("Input role not valid.");//log4j
				System.out.println();
				System.out.println("Please enter role: customer or admin or employee ");
			}

	}	while(flag!=true);
		return input;
	}
	
}
