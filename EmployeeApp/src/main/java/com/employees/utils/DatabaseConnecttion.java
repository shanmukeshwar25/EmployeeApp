package com.employees.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseConnecttion {
	public static Properties property = new Properties();
    public static Connection startConnection() {
	   Scanner sc = new Scanner(System.in);
		try (InputStream input = new FileInputStream("src/main/resources/postgres.properties")) {
			property.load(input);
			String url = property.getProperty("db.url");
			String username = property.getProperty("db.username");
			String password = property.getProperty("db.password");

			 return DriverManager.getConnection(url, username, password);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
   }
}
  