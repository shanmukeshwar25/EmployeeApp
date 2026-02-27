package com.employees.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public static Connection startConnection() throws SQLException, IOException {
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");

		if (url == null || username == null || password == null) {
			throw new SQLException("Missing DB environment variables");
		}

		Connection conn = DriverManager.getConnection(url, username, password);
		if (conn == null || conn.isClosed()) {
			throw new SQLException("Failed to establish database connection");
		}

		return conn;
	}
}
