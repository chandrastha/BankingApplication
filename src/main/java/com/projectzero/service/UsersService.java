package com.projectzero.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projectzero.LoginScreen;
import com.projectzero.dao.UsersDAOImpl;
import com.projectzero.model.Users;

public class UsersService {

	private UsersDAOImpl uDao = new UsersDAOImpl();
	
	public UsersService() {};
	
	public UsersService(UsersDAOImpl uDao) {
		this.uDao = uDao;
	};
	
	
	 
	//verify username and password for login
	public boolean login(String username, String password) {
		boolean isValidUser = false;
		try {		
			if(username!=null && password!=null) {
				Users user = uDao.getUser(username,password);
			if(user==null) {
				isValidUser = false;
			}
			else isValidUser = true;
			
		}}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		return isValidUser;
	}
	
	//adding user into the database
	public void addUser(String username, String password, String role) {
		try {		
			if(username!=null && password!=null && role!=null) {
				uDao.preparedInsertUser(username,password,role);

		}}
		catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public int getRole(String username, String password) {
		int role = 0;
		try {		
			if(username!=null && password!=null) {
				Users user = uDao.getUser(username,password);
				if(user.getRole().equals("admin")) {
					role = 1;
				}
				if(user.getRole().equals("employee")) {
					role = 2;
				}
				if(user.getRole().equals("customer")) {
					role = 3;
				}
			
		}}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		return role;
	}
	
	//get user id 
	public int getUserID(String Username, String password) {
		int userid = 0;
		Users user =null;
		user = uDao.getUser(Username, password);
		userid = user.getUserId();
		
		return userid;
		
	}
	
//	check if user exists or not
	public boolean checkIfUserExistsOrNot(int userId) {
		boolean userExists = false;
		try {
			userExists = uDao.checkIfUserExistsOrNot(userId);

			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}
		
		return userExists;
		
	}

	public Users getUsersDetails(int userId) {
		Users user = null;
		user = uDao.getUser(userId);
		return user;
	}
	
	
	
}
