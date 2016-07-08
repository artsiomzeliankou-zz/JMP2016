package dry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcUtils {

	private static boolean initialized;

	public JdbcUtils() {
	}

	public static Connection getConnection() throws SQLException {
		initDriver();
		return DriverManager.getConnection("localhost:4000/localdb");
	}

	private static synchronized void initDriver() {
		if (!initialized) {
			try {
				Class.forName("db.driver.Name");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			initialized = true;
		}
	}
}
