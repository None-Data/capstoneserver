package capstone.probablymainserver.capstoneserver;

import java.sql.*;

public class DatabaseUtil {
	private static final String url = "Unknown"
			+ "";
	private static final String username = "Unknown";
	private static final String password = "Unknown";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	public static void close(AutoCloseable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try { if (rs != null) rs.close(); } catch (Exception e) {}
		try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		try { if (conn != null) conn.close(); } catch (Exception e) {}
	}
}
