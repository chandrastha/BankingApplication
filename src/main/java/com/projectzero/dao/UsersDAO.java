package com.projectzero.dao;

import java.util.List;

import com.projectzero.model.Users;

public interface UsersDAO {
	
	//prepared statement to insert user
	void preparedInsertUser(String userName, String passWord, String role);
	
	//get all users
	List<Users> getAllUsers();
	
	//create or insert user
	void insertUser(Users user);
	
	//get users by username
	Users getUser(String name);
	
	//get users by userId
	Users getUser(int id);
	
	//delete user
	boolean deleteUser(int id);
	
	//get user details buy username and password
	Users getUser(String username, String password);
	
}
