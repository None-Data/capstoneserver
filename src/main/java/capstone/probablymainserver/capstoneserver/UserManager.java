package capstone.probablymainserver.capstoneserver;

import java.sql.*;

public class UserManager {
	
	public int registerUser(String UserID, String PassWord) {
    	Connection conn = null;
    	PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
        try {
        		conn = DatabaseUtil.getConnection();
        		
        		if (checkUserID(UserID) == true) { // 아이디 중복 검사
    				return 5; // 아이디 중복
    			}
        		String checkSql = "SELECT uid FROM user ORDER BY uid desc limit 1";
        		checkStmt = conn.prepareStatement(checkSql);
        		ResultSet rs = checkStmt.executeQuery();
        		int uid = 0;
        		
        		if(rs.next()) {
        			uid = rs.getInt("uid");
        		}
        		
        		uid += 1;
        		String myUid = Integer.toString(uid);
        		
        		String updateSql = "INSERT INTO user (username, userpw, nickname) VALUES (?, ?, ?)";
        		updateStmt = conn.prepareStatement(updateSql);

        		updateStmt.setString(1, UserID);
        		updateStmt.setString(2, PassWord);
        		updateStmt.setString(3, "user" + myUid/*임의의 이름*/);

        		int rows = updateStmt.executeUpdate();
        		if (rows > 0) { // 성공 여부 확인
        			return 1; // 회원 가입 성공
        		} else {
        			return 2; // 회원 가입 실패
        		}

        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
        }
        
        finally {
        	DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
    }
	
	public int loginUser(String UserID, String PassWord) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int myUID;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (checkUserID(UserID) == false) { // 아이디 존재 확인
				return 3; // 존재하지 않는 아이디
			}
			String sql = "SELECT uid FROM user WHERE username = ? AND userpw = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, UserID);
			stmt.setString(2, PassWord);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				myUID = rs.getInt("uid");
				SessionManager.setLoggedInUid(myUID);
				return 1; // 로그인 성공
			} else {
				return 4; // 로그인 실패 사유 : 비밀번호 틀림
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public boolean checkUserID(String UserID) {
		String sql = "SELECT uid FROM user WHERE username = ?";

        try (
        	Connection conn = DatabaseUtil.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
            	return true;
            } else {
            	return false;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	/*
	public int showUserData() { -> 무슨 정보를 보여줄건지?
		return 0;
	}
	*/ 
	
	public int updateUserID(String newUserID) {
		int uid = SessionManager.getLoggedInUid();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (checkUserID(newUserID) == true) { // 아이디 존재 확인
				return 3; // 존재하는 아이디
			}
			
			String sql = "UPDATE user SET username = ? WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newUserID);
			stmt.setInt(2, uid);
						
			int rows = stmt.executeUpdate();
			if (rows > 0) { // 아이디 수정 성공 여부
				return 1; // 아이디 수정 성공
			} else {
				return 2; // 아이디 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int updateUserPW(String newPassWord) {
		int uid = SessionManager.getLoggedInUid();
		
		Connection conn = null;
		PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String checkSql = "SELECT userpw FROM user WHERE uid = ? AND userpw = ?";
			checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setInt(1, uid);
			checkStmt.setString(2, newPassWord);
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) {
				return 5; // 이미 사용중인 비밀번호
			}
			
			String updateSql = "UPDATE user SET userpw = ? WHERE uid = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setString(1, newPassWord);
			updateStmt.setInt(2, uid);
						
			int rows = updateStmt.executeUpdate();
			if (rows > 0) { // 비밀번호 수정 성공 여부
				return 1; // 비밀번호 수정 성공
			} else {
				return 2; // 비밀번호 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public int deleteUser(String PassWord) {
		int uid = SessionManager.getLoggedInUid();
		
		Connection conn = null;
		PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String checkSql = "SELECT userpw FROM user WHERE uid = ? AND userpw = ?";
			checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setInt(1, uid);
			checkStmt.setString(2, PassWord);
			ResultSet rs = checkStmt.executeQuery();
			
			if (!rs.next()) {
				return 4; // 비밀번호 틀림
			}
			
			String updateSql = "DELETE FROM user WHERE uid = ? AND userpw = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setInt(1, uid);
			updateStmt.setString(2, PassWord);
						
			int rows = updateStmt.executeUpdate();
			if (rows > 0) { // 회원탈퇴 성공 여부
				SessionManager.clearSession();
				return 1; // 회원탈퇴 성공
			} else {
				return 2; // 회원탈퇴 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
	}
}
