package com.employees.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Jdbc {
	public static void jdbc() {
		Scanner sc = new Scanner(System.in);
		Properties property = new Properties();
		try (InputStream input = new FileInputStream("src/main/resources/postgres.properties")) {
			property.load(input);
			String url = property.getProperty("db.url");
			String username = property.getProperty("db.username");
			String password = property.getProperty("db.password");

			Connection con = DriverManager.getConnection(url, username, password);
			
			// print the table 
			Statement stat = con.createStatement();
			String query = "select * from employee";
			ResultSet rs = stat.executeQuery(query);

			while (rs.next()) {
				System.out.println("id: " + rs.getInt("id") + " name: " + rs.getString("name") + " age: "
						+ rs.getInt("age"));
			}
			System.out.println();
			
//			// print data of specific id
//			String query1 = "SELECT * FROM employee WHERE id = ?;";
//			System.out.println("Enter the id : ");
//			int id = sc.nextInt();
//			PreparedStatement pstat = con.prepareStatement(query1);
//			pstat.setInt(1, id);
//			
//			ResultSet rsup = pstat.executeQuery();
//			if(rsup.next()) {
//				System.out.println("id: " + rsup.getInt("id") + " name: " + rsup.getString("name") + " age: "
//						+ rsup.getInt("age"));
//			}
//			
//			//update query 
//			String updateQuery =  "update employee set name=? where id=?";
//			System.out.print("Enter the id: ");
//			int id1 = sc.nextInt();
//			System.out.print("Enter the new name : ");
//			String name = sc.next();
//			PreparedStatement psupt = con.prepareStatement(updateQuery);
//			psupt.setInt(2, id1);
//			psupt.setString(1, name);
//			int rowaffected = psupt.executeUpdate();
//			System.out.println("no of rows updated: "+rowaffected);
			
			// delete query
			String deletequery = "delete from employee where id=?";
			System.out.print("Enter the id: ");
			int id2 = sc.nextInt();
			PreparedStatement psdel = con.prepareStatement(deletequery);
			psdel.setInt(1, id2);
			
			int r = psdel.executeUpdate();
			System.out.println("deleted: "+r);
					
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
