package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 요청 DTO (Data Transfer Object)
record LoginRequest(String userId, String password) {}

// 로그인 응답 DTO
record LoginResponse(String accessToken) {}

// 회원가입 요청 DTO
record SignupRequest(String userId, String password) {}

//회원탈퇴 요청 DTO
record DeleteAccountRequest(String password) {}

@RestController
@RequestMapping("/api")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider; // JWT 생성기 (아래에서 만들 예정)

    public LoginController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    	System.out.printf("[Log] /api/login ");
        // 1. 직접 로그인 메서드로 사용자 인증
        int uid = capstone.Login(loginRequest.userId(), loginRequest.password());

        if (uid > 0) {
            // 2. 인증 성공 시 JWT 생성
            String token = jwtTokenProvider.createToken(uid);
            System.out.printf("(userId: %s, uid: %d)\n", loginRequest.userId(), uid);
            // 3. 생성된 토큰을 응답
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            // 4. 인증 실패 시 에러 응답
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Integer> signup(@RequestBody SignupRequest request) {
    	System.out.println("[Log] /api/signup id=" + request.userId());
    	
        int success = capstone.registerUser(request.userId(), request.password());
        return ResponseEntity.ok(success);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        // JWT 필터를 통해 이미 인증된 사용자 정보가 SecurityContext에 있음
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int)auth.getPrincipal();  // 토큰에서 추출된 uId

        System.out.printf("[Log] auth/me commanded ");

        // userId로 사용자 조회 (비밀번호는 필요 없음)
        User user = capstone.getUser(uid); // 또는 userService.findUser(userId)

        if (user == null) {
            return ResponseEntity.status(401).body("유저 정보 없음");
        }
        System.out.printf("(uid: %d)\\n", uid);
        user.setUid(0);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/update")
    public ResponseEntity<Integer> updateUserData(@RequestBody User user)
    {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        System.out.printf("[Log] auth/update commanded ");
        User prev = capstone.getUser(uid);	// 얘는 uid, userID,tools,banned, ing 밖에 없음!
        System.out.printf("(uid: %d)\n", uid);
        
        if (!user.getUserId().equals(prev.getUserId()))
        {
        	capstone.updateUserID(user.getUserId(), uid);
        }
        if (!user.getPassword().equals(prev.getPassword()))
        {
        	capstone.updateUserPW(user.getPassword(), uid);
        }
        if (user.getTools() != prev.getTools())
        {
        	capstone.updateUserTools(user.getTools(), uid);
        }
        if (user.getBanned() != prev.getBanned())
        {
        	capstone.updateUserAllergy(user.getBanned(), uid);
        }
        
        for (Ingredient ing : prev.getIngredients())
        {
        	if (!user.getIngredients().contains(ing))
        	{
        		capstone.removeIngredient(ing, uid);
        	}
        }
        for (Ingredient ing : user.getIngredients())
        {
        	if (!prev.getIngredients().contains(ing))
        	{
        		capstone.addIngredient(ing, uid);
        	}
        }
        return ResponseEntity.ok(1);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteAccount(@RequestBody DeleteAccountRequest pw)
    {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        System.out.printf("[Log] auth/delete commanded ");
        System.out.printf("(uid: %d)\n", uid);
        
        return ResponseEntity.ok(capstone.unsubscribeUser(uid, pw.password()));
    }
    
}