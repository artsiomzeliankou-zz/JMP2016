package com.epam.jmp.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionProvider {

	private static boolean initialized;

	public ConnectionProvider() {
	}

	public static Connection getConnection() throws SQLException {
		initDriver();
		return DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER_NAME, Constants.DB_USER_PASSWORD);
	}

	private static synchronized void initDriver() {
		if (!initialized) {
			try {
				Class.forName(Constants.DRIVER_NAME);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			initialized = true;
		}
	}
}
