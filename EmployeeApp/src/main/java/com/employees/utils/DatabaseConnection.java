package com.employees.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private static final String PROPERTIES_FILE = "postgres.properties";

	public static Connection startConnection() throws SQLException, IOException {
		Properties properties = new Properties();
		try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {

			if (input == null) {
				throw new IOException("Properties file not found: " + PROPERTIES_FILE);
			}

			properties.load(input);
		}

		String url = properties.getProperty("db.url");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");

		if (url == null || username == null || password == null) {
			throw new IOException("Missing DB configuration values in " + PROPERTIES_FILE);
		}

		Connection conn = DriverManager.getConnection(url, username, password);
		if (conn == null || conn.isClosed()) {
			throw new SQLException("Failed to establish database connection");
		}

		return conn;
	}
}
