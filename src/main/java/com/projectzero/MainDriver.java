package com.projectzero;

import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class MainDriver {
	
	public static boolean isRunning = true;
	public final static Logger log = Logger.getLogger(MainDriver.class);
	
	public static void main(String[] args) {

		//set level for logger to WARNINGs
		log.setLevel(Level.ERROR);
		
		log.trace("This is tracing.");
		log.debug("This is debugging.");
		log.info("This is info.");
		log.warn("This is warning.");
		log.error("This is error. ");
		log.fatal("This is fatal.");
		
		
		//Creating a console application 
		Scanner conScan = new Scanner(System.in);
		Screen presentScreen = new MenuScreen();
		
		while(isRunning) {
			presentScreen.render(conScan);
		}
		
		conScan.close();
		System.out.println("Thank you for using our application.");
		
	}

}
