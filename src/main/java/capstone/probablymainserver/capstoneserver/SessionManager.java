package capstone.probablymainserver.capstoneserver;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


public class SessionManager {
	
	private static int loggedInUid = -1; //-1은 비로그인 상태
	public static final Map<String, TempUserData> tempUsers = new HashMap<>();
	
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
	
	public static int getUserid(String id, String pw)
	{
		TempUserData tu = tempUsers.get(id);
		if (tu == null) return -1;
		else if (tu.getPassword().equals(tu)) return tu.getUid();
		else return -1;
	}
}