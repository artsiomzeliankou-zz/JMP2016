package dry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				throw new RuntimeException("ERROR: Can't initialize driver");
			}
			initialized = true;
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(Connection cn) {
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
