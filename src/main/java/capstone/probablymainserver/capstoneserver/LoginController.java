package capstone.probablymainserver.capstoneserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 요청 DTO (Data Transfer Object)
record LoginRequest(String userId, String password) {}

// 로그인 응답 DTO
record LoginResponse(String accessToken) {}

@RestController
@RequestMapping("/api")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider; // JWT 생성기 (아래에서 만들 예정)

    public LoginController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. 직접 만드신 로그인 메서드로 사용자 인증
        boolean isAuthenticated = capstone.Login(loginRequest.userId(), loginRequest.password()) != null;

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
}