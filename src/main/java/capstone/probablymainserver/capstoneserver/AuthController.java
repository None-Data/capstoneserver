package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserManager um = new UserManager();
	private capstone cap = new capstone();
	@PostMapping("/signup")
	public ResponseEntity<Integer> signUp(@RequestBody List<String> data)	// data[0]: UserID, data[1]: Password
	{
		System.out.println("[Log] auth/signUp commanded " + data.get(0) + " " + data.get(1));
		
		return ResponseEntity.ok(um.registerUser(data.get(0), data.get(1)));
		// DB 등록 과정 이후 오류 검증 후 해당 오류 내용을 코드화 해서 반환
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody List<String> data)
	{
		System.out.println("[Log] auth/login commanded " + data.get(0) + " " + data.get(1));
		
		User user = cap.Login(data.get(0), data.get(1));
		
		if (user == null) {
			// 로그인 실패
			return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호 오류");
		}
		
		TempUserData tu = new TempUserData();
		tu.setUserName(data.get(0));
		tu.setPassword(data.get(1));
		tu.setUid(user.getUid());
		SessionManager.tempUsers.put(data.get(0), tu);
		user.setUid(0);
		// DB에 정보 매칭 이후 오류 내용 혹은 로그인..?
		return ResponseEntity.ok(user);
	}
	@GetMapping("/logout")
	public ResponseEntity<Integer> logout(@RequestBody List<String> data)
	{
		System.out.println("[Log] auth/logout commanded " + data.get(0) + " " + data.get(1));
		
		if (SessionManager.getUserid(data.get(0), data.get(1)) > -1)
		{
			SessionManager.tempUsers.remove(data.get(0));
			return ResponseEntity.ok(1);
		}
		return ResponseEntity.ok(0);
	}
	@GetMapping("/me")
	public ResponseEntity<?> me(@RequestBody List<String> data)
	{
		System.out.println("[Log] auth/me commanded " + data.get(0) + " " + data.get(1));
		
		User user = cap.Login(data.get(0), data.get(1));
		
		if (user == null)
		{
			return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호 오류");
		}
		user.setUid(0);
		// 유저 정보 로딩
		return ResponseEntity.ok(user);
	}
	@PutMapping("/update")
	public ResponseEntity<Integer> update(@RequestBody User user)
	{
		System.out.println("[Log] auth/update commanded");
		
		// 유저 정보 갱신
		return null;
	}
}
