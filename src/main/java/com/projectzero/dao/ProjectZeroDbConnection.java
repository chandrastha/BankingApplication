package com.projectzero.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProjectZeroDbConnection {
	private static final String URL = "jdbc:postgresql://reva.c7cch2klrsmh.us-east-2.rds.amazonaws.com:5432/projectzerodb";
	private static final String username = "projectzerouser";
	private static final String password = "Passw0rd";
	
	public Connection getDbConnection() throws SQLException{
		return DriverManager.getConnection(URL, username, password);
	}
}
