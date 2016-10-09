package by.gsu.epamlab.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;

public final class JdbcUtils {

	private static boolean initialized;
	private static Logger logger = Logger.getLogger(JdbcUtils.class);

	public JdbcUtils() {

	}

	public static Connection getConnection() throws SQLException {
		initDriver();
		
		return DriverManager.getConnection(Constants.DB_URL,
				Constants.DB_LOGIN, Constants.DB_PASSWORD);
	}

	private static synchronized void initDriver() {
		if (!initialized) {
			try {
				
				/**
				 * Next code invokes loading DB Driver class,
				 * which have static method register()
				 * so we have here incapsulated registering
				 * of driver by itself ! 
				 */
				Class.forName(Constants.DRIVER_NAME);
				
				/**
				 * This is the alternative but non-safe method of register JDBC Driver
				 * because we can get 2 registered drivers together
				 * 
				 * Driver dbDriver = DriverManager.getDriver(Constants.DRIVER_NAME);
				 * DriverManager.registerDriver(dbDriver);
				 */

				logger.debug(Messages.SUCCESS_INIT_JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				logger.error(Messages.ERROR_INIT_JDBC_DRIVER);
				;
				throw new RuntimeException(Messages.ERROR_INIT_DRIVER);
			}
			initialized = true;
		}

	}

	public static void closeQuietly(ResultSet... args) {
		for (ResultSet rs : args) {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn(Messages.ERROR_CLOSE_RESULT_SETS + e);
				}

			}
		}

	}

	public static void closeQuietly(Statement... args) {
		for (Statement st : args) {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					logger.warn(Messages.ERROR_CLOSE_STATEMENTS + e);
				}

			}
		}

	}

	public static void closeQuietly(Connection cn) {
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				logger.error(Messages.ERROR_CLOSE_CONNECTION + e);
			}

		}

	}

}
