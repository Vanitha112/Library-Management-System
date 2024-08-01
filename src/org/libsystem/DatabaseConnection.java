package org.libsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/Librarydb";
	private static final String USER = "root";
	private static final String PASSWORD = "vanithasql";
	public static Connection getconnection() {
		Connection connection=null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			System.out.println("Failed Connection");
		}
		return connection;
	}
		

}
