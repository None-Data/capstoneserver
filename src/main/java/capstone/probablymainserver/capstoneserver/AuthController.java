package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserManager um = new UserManager();
	@PostMapping("/signup")
	public ResponseEntity<Integer> signUp(@RequestBody List<String> data)	// data[0]: UserID, data[1]: Password
	{
		System.out.println("[Log] auth/signUp commanded");
		
		return ResponseEntity.ok(um.registerUser(data.get(0), data.get(1)));
		// DB 등록 과정 이후 오류 검증 후 해당 오류 내용을 코드화 해서 반환
	}
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody List<String> data)
	{
		System.out.println("[Log] auth/login commanded");
		
		
		// DB에 정보 매칭 이후 오류 내용 혹은 로그인..?
		return null;
	}
	@GetMapping("/me")
	public ResponseEntity<User> me(@RequestBody List<String> data)
	{
		System.out.println("[Log] auth/me commanded");
		
		// 유저 정보 로딩
		return null;
	}
	@PutMapping("/update")
	public ResponseEntity<Integer> update(@RequestBody User user)
	{
		System.out.println("[Log] auth/update commanded");
		
		// 유저 정보 갱신
		return null;
	}
}
