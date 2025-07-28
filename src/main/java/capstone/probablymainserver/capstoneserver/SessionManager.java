package capstone.probablymainserver.capstoneserver;

import java.util.Dictionary;


public class SessionManager {
	
	private static int loggedInUid = -1; //-1은 비로그인 상태
	
	// 로그인 시 uid 저장
	public static void setLoggedInUid(int uid) {
		loggedInUid = uid;
	}
	
	// 현재 로그인 한 uid 반환
	public static int getLoggedInUid() {
		return loggedInUid;
	}
	
	// 로그아웃 시 uid 초기화
	public static void clearSession() {
		loggedInUid = -1;
	} 
	
	// 로그인 상태 확인
	public static boolean isLoggedIn() {
		return loggedInUid != -1;
	}
	
}
