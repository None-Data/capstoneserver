package capstone.probablymainserver.capstoneserver;

import java.sql.*;

public class DatabaseUtil {
	private static final String url = "jdbc:mysql://database-capstone.c1uq8cggg6iv.ap-northeast-2.rds.amazonaws.com:3306/recipe?useSSL=false&serverTimezone=UTC\r\n"
			+ "";
	private static final String username = "admin";
	private static final String password = "Ygnx57913!";
	
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
