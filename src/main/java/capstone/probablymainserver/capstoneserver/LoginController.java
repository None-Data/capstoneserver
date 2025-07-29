package capstone.probablymainserver.capstoneserver;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 요청 DTO (Data Transfer Object)
record LoginRequest(String userId, String password) {}

// 로그인 응답 DTO
record LoginResponse(String accessToken) {}

// 회원가입 요청 DTO
record SignupRequest(String id, String pw) {}

@RestController
@RequestMapping("/api")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider; // JWT 생성기 (아래에서 만들 예정)

    public LoginController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    	System.out.println("[Log] /api/login 요청 도착");
        // 1. 직접 로그인 메서드로 사용자 인증
        boolean isAuthenticated = capstone.Login(loginRequest.userId(), loginRequest.password());

        if (isAuthenticated) {
            // 2. 인증 성공 시 JWT 생성
            String token = jwtTokenProvider.createToken(loginRequest.userId());
            
            // 3. 생성된 토큰을 응답
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            // 4. 인증 실패 시 에러 응답
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Integer> signup(@RequestBody SignupRequest request) {
    	System.out.println("[Log] /api/signup 요청 도착: id=" + request.id());
        int success = UserManager.registerUser(request.id(), request.pw());
        return ResponseEntity.ok(success);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        // JWT 필터를 통해 이미 인증된 사용자 정보가 SecurityContext에 있음
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) auth.getPrincipal();  // 토큰에서 추출된 userId

        System.out.println("[Log] auth/me commanded " + userId);

        // userId로 사용자 조회 (비밀번호는 필요 없음)
        User user = capstone.getUser(userId); // 또는 userService.findUser(userId)

        if (user == null) {
            return ResponseEntity.status(401).body("유저 정보 없음");
        }

        user.setUid(0);
        return ResponseEntity.ok(user);
    }

    
}