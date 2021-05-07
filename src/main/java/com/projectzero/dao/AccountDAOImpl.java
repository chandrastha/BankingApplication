package com.projectzero.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.projectzero.model.Account;

public class AccountDAOImpl implements AccountDAO   {
	
	private ProjectZeroDbConnection ZeroCon;
	
	private static final String CHECK_BALANCE = "select balance from account where accountid = ?";
	private static final String DEPOSIT = "update account set balance = balance + ? where accountid = ?";
	private static final String WITHDRAW = "update account set balance = balance - ? where accountid = ?";
//	private static final String TRANSFER = First withdraw and then deposit;
	private static final String CHECK_STATUS = "select status from account where accountid = ?";
	private static final String DELETE_ACCOUNT = "delete from account where accountid = ?";
	private static final String INSERT_INTO_ACCOUNT = "insert into account(balance,status,type,joined) values (?,?,?,FALSE) returning accountid";
	private static final String INSERT_INTO_JOINT_ACCOUNT="insert into account(balance,status,type,joined) values (?,?,?,TRUE) returning accountid";
	private static final String GET_ACCOUNT_ID = "select accountid from users_junction_account where userid = ?";
	private static final String GET_INACTIVE_ACCOUNTS = "select * from account where status = 'inactive' order by accountid asc";
	private static final String GET_ACCOUNT_INFORMATION = "select * from account where accountid = ?";
	private static final String APPROVE_ACCOUNT = "update account set status ='active' where accountid = ?"; 
	private static final String DENY_ACCOUNT = "update account set status ='inactive' where accountid = ?"; 
	private static final String INSERT_INTO_JUNCTION_TABLE = "insert into users_junction_account values (?,?);"; 
	private static final String CHECK_IF_ACCOUNT_EXISTS = "select accountid from account where accountid = ?";

	public AccountDAOImpl() {
		ZeroCon = new ProjectZeroDbConnection();
	}
	
//check account balance
	@Override
	public double checkBalance(int accountid) {
		double accountBalance = 0.0;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(CHECK_BALANCE);
			ps.setInt(1, accountid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountBalance = rs.getDouble("balance");
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountBalance;
	}

	//check account information
	public List<Account> getAccountInformation(int accountid) {
		List<Account> accountList = new ArrayList<>();
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(GET_ACCOUNT_INFORMATION);
			ps.setInt(1, accountid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountList.add(new Account(rs.getInt(1),rs.getDouble(2), rs.getString(3), rs.getString(4)));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}
	
//deposit into account
	@Override
	public boolean deposit(double amount, int accountId) {
		boolean depositSuccessful = false;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(DEPOSIT);
			ps.setDouble(1, amount);
			ps.setInt(2, accountId);
			
			depositSuccessful = ps.executeUpdate()>0;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return depositSuccessful;
	}
	
//withdraw from account
	@Override
	public boolean withdraw(double amount, int accountId) {
		boolean withdrawSuccessful = false;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(WITHDRAW);
			ps.setDouble(1, amount);
			ps.setInt(2, accountId);
			
			withdrawSuccessful = ps.executeUpdate()>0;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return withdrawSuccessful;
	}
	
//transfer balance
	@Override
	public boolean transfer(double amount, int fromAccountId, int toAccountId ) {
		boolean transferSuccessful = false;
		boolean depositSuccessful = false;
		boolean withdrawSuccessful = false;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(WITHDRAW);
			ps.setDouble(1, amount);
			ps.setInt(2, fromAccountId);
			depositSuccessful = ps.executeUpdate()>0;
			
			PreparedStatement ps1 = con.prepareStatement(DEPOSIT);
			ps1.setDouble(1, amount);
			ps1.setInt(2, toAccountId);
			withdrawSuccessful = ps1.executeUpdate()>0;
			
			if(withdrawSuccessful && depositSuccessful) {
				transferSuccessful = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return transferSuccessful;
	}
	
//check account status
	@Override
	public String checkStatus(int accountId) {
		String isActive = null;
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(CHECK_STATUS);
			ps.setInt(1, accountId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				isActive = rs.getString("status");
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return isActive;
	}
	
//inserting values into account
	@Override
	public Account createAccount(double balance, String status, String type ) {
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(INSERT_INTO_ACCOUNT);
			ps.setDouble(1,balance );
			ps.setString(2,status );
			ps.setString(3, type);
			
			int changed  = ps.executeUpdate();
			System.out.println("number of rows changed"+changed);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//deleting account 
	@Override
	public boolean deleteAccount(int accountId) {
		boolean isDeleted = false;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(DELETE_ACCOUNT);
			ps.setInt(1, accountId);
			
			isDeleted  = ps.executeUpdate()>0;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
			return isDeleted ;
	}
	
//get account id for userid from junction table
	public int getAccountId(int userId){
		int accountId = 0;
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(GET_ACCOUNT_ID);
			ps.setInt(1, userId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountId = rs.getInt("accountid");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountId;
		
	}
	
//generate list of inactive accounts
	public List<Account> getInactiveAccounts(){
		List<Account> accountList = new ArrayList<>();
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(GET_INACTIVE_ACCOUNTS);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				accountList.add(new Account(rs.getInt(1),rs.getDouble(2), rs.getString(3), rs.getString(4)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

//	approve deny account
	public int approveDenyAccount(int approveDenyId, int approveDenyChoice) {
		int isUpdated = 0;
		try(Connection con = ZeroCon.getDbConnection()){
			if(approveDenyChoice==1) {
				PreparedStatement ps = con.prepareStatement(APPROVE_ACCOUNT);
				ps.setInt(1, approveDenyId);
				 ps.executeUpdate();
				 isUpdated  = 1;
				
			}else if(approveDenyChoice==2) {
				PreparedStatement ps = con.prepareStatement(DENY_ACCOUNT);
				ps.setInt(1, approveDenyId);
				 ps.executeUpdate();
				 isUpdated  = -1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
//	view all accounts
	public List<Account> viewAllAccounts() {
		List <Account> accountList = new ArrayList<>();
		try(Connection con = ZeroCon.getDbConnection()){

			PreparedStatement ps = con.prepareStatement("select * from account order by account.type, account.accountid asc");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				accountList.add(new Account(rs.getInt(1),rs.getDouble(2), rs.getString(3), rs.getString(4)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}
	
//creating new account
	public int createNewAccount(String type ) {
		int accountID = 0;
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(INSERT_INTO_ACCOUNT);
			ps.setDouble(1,0.0 );
			ps.setString(2,"inactive");
			ps.setString(3, type);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountID= rs.getInt(1);
			}
			
			
//			System.out.println("number of account created: "+changed);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accountID;
	}
	
//create new customer joint account
	public int createNewJointCustomerAccount() {
		int accountId = 0;
		try (Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(INSERT_INTO_JOINT_ACCOUNT);
			ps.setDouble(1,0.0 );
			ps.setString(2,"inactive");
			ps.setString(3,"customer");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountId= rs.getInt(1);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountId;
	}
	
	
	
//	inserting into a junction table
	public void createJunctionTable(int userid, int accountid) {
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(INSERT_INTO_JUNCTION_TABLE);
			ps.setInt(1,userid );
			ps.setInt(2,accountid);
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
//check if account exists or not
	public boolean checkAccountExistOrNot(int accountid) {
		boolean accountExists = false;
		int accountID = 0;
		
		try(Connection con = ZeroCon.getDbConnection()){
			PreparedStatement ps = con.prepareStatement(CHECK_IF_ACCOUNT_EXISTS);
			ps.setInt(1, accountid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accountID = rs.getInt(1);
			}
			if(accountid==accountID) {
				accountExists = true;
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountExists;
	}
	
	//get user id from junction table
	 public int getUserIdFromJunctionTable(int accountid){
	 	int userId =0;
	 	
	 	try(Connection con =ZeroCon.getDbConnection()){
	 		PreparedStatement ps = con.prepareStatement("select userid from users_junction_account where accountid = ?");
			ps.setInt(1, accountid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userId = rs.getInt(1);
			}
		
	 	}catch(SQLException e) {
			e.printStackTrace();
		}
	 	return userId;
	 }
	 
	 //get multiple user id from junction table
//	 public int[] getUsersIdFromJunctionTable(int accountid){
//			int[] userId = new int[2];
//
//	 	
//	 	try(Connection con =ZeroCon.getDbConnection()){
//	 		PreparedStatement ps = con.prepareStatement("select userid from users_junction_account where accountid = ?");
//			ps.setInt(1, accountid);
//			
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				userId[0] = rs.getInt(1);
//				userId[1] = rs.getInt(2);
//			}
//		
//	 	}catch(SQLException e) {
//			e.printStackTrace();
//		}
//	 	return userId;
//	 }
//	 
	 //get multiple user id from junction table

	public List<Integer> getUsersIdFromJunctionTable(int accountid){
			ArrayList <Integer> userIdList = new ArrayList<Integer>();

	 	
	 	try(Connection con =ZeroCon.getDbConnection()){
	 		PreparedStatement ps = con.prepareStatement("select userid from users_junction_account where accountid = ?");
			ps.setInt(1, accountid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				userIdList.add(rs.getInt(1));
			}
		
	 	}catch(SQLException e) {
			e.printStackTrace();
		}
	 	return userIdList;
	 }

	 ///check if account is joined
	 public boolean checkIfAccountIsJoined(int accountid){
		 	boolean isJoined = false; 
		 	
		 	try(Connection con =ZeroCon.getDbConnection()){
		 		PreparedStatement ps = con.prepareStatement("select joined from account where accountid = ?");
				ps.setInt(1, accountid);
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					isJoined = rs.getBoolean(1);
				}
			
		 	}catch(SQLException e) {
				e.printStackTrace();
			}
		 	return isJoined;
		 }
	
}
