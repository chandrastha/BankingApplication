package com.projectzero.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projectzero.model.Users;

public class UsersDAOImpl implements UsersDAO{
	
	private ProjectZeroDbConnection ZeroCon;
	
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String INSERT_USERS_SQL = "insert into users(username,password,role) values (?,?,?)";
	private static final String SELECT_USERS_BY_ID = "select userid, username,password, role from users where userid = ? ";
	private static final String SELECT_USERS_BY_NAME ="select userid, username,password, role from users where username = ? " ;
	private static final String DELETE_USERS_SQL = "delete from users where userid = ?";
	private static final String GET_USER_BY_USERNAME_PASSWORD = "select * from users where username = ? and password = ?";
	private static final String CHECK_IF_USER_EXISTS = "select userid from users where userid = ?";
	
	public UsersDAOImpl() {
		ZeroCon = new ProjectZeroDbConnection();
	}

	//INSERT INTO USER working 
	@Override
	public void preparedInsertUser(String userName, String passWord, String role) {
	
		try(Connection con = ZeroCon.getDbConnection()){
//			String sql = "insert into users(username,password,role) values (?,?,?)";
			
			PreparedStatement prepStatement = con.prepareStatement(INSERT_USERS_SQL);
			prepStatement.setString(1, userName);
			prepStatement.setString(2, passWord);
			prepStatement.setString(3,role);
			
			int changed  = prepStatement.executeUpdate();
			System.out.println(changed +" user added. Account created.");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//GET ALL USERS working
	@Override
	public List<Users> getAllUsers() {
		List <Users> usersList = new ArrayList<>();
		try (Connection con = ZeroCon.getDbConnection()){
//			String sql= "select * from users";
			
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				usersList.add(new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	//INSERT USER
	@Override
	public void insertUser(Users user) {
		// TODO Auto-generated method stub
		
	}
	
	//GET USER BY USERNAME working
	@Override
	public Users getUser(String name) {
		Users user = new Users();
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(SELECT_USERS_BY_NAME);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("userid");
				String password = rs.getString("password");
				String role= rs.getString("role");
				
				user = new Users(id, name, password, role);
			}
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//GET USERS BY USERID working
	@Override
	public Users getUser(int id) {
		Users user = null;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement  ps = con.prepareStatement(SELECT_USERS_BY_ID);
			ps.setInt(1, id);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				
				user = new Users(id, name, password, role);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	//Delete users by userId working
	@Override
	public boolean deleteUser(int id) {
		boolean rowDeleted = false;
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(DELETE_USERS_SQL);
			ps.setInt(1, id);
			rowDeleted = ps.executeUpdate()>0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}
	
	//Get user by username and password working
	@Override
	public Users getUser(String username, String password) {
		Users user = null;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement  ps = con.prepareStatement(GET_USER_BY_USERNAME_PASSWORD);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String pword = rs.getString("password");
				String role = rs.getString("role");
				user = new Users(id, name, pword, role);
			}
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean checkIfUserExistsOrNot(int userId) {
		boolean userExists = false;
		int userID = 0;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(CHECK_IF_USER_EXISTS);
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userID = rs.getInt(1);
			}
			if(userID==userId) {
				userExists = true;
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}

//	public Users getUserDetails(int userId) {
//		// TODO Auto-generated method stub
//		
//		
//		Users user = null;
//		
//		try(Connection con = ZeroCon.getDbConnection()){
//			PreparedStatement  ps = con.prepareStatement(GET_USER_BY_USERNAME_PASSWORD);
//			ps.setString(1, username);
//			ps.setString(2, password);
//
//			ResultSet rs = ps.executeQuery();
//			
//			while(rs.next()) {
//				int id = rs.getInt("userid");
//				String name = rs.getString("username");
//				String pword = rs.getString("password");
//				String role = rs.getString("role");
//				user = new Users(id, name, pword, role);
//			}
//		
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
//		return user;
//		
//		
//		return null;
//	}

}
