package capstone.probablymainserver.capstoneserver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long validityInMilliseconds;

    // application.properties 파일에서 설정값 주입
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-in-ms}") long validityInMs) {
    	 System.out.println("!!! [DEBUG] SERVER IS USING JWT KEY: " + secretKey);
		System.out.println("Raw secretKey: [" + secretKey + "]");
		secretKey = secretKey.trim();
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.validityInMilliseconds = validityInMs;
	}

    // 사용자 ID를 기반으로 토큰 생성
    public String createToken(int uid) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setSubject(Integer.toString(uid)) // 토큰의 주체 (사용자 ID)
            .setIssuedAt(now)   // 발급 시간
            .setExpiration(validity) // 만료 시간
            .signWith(key, SignatureAlgorithm.HS256) // 서명
            .compact();
    }
    
    // 토큰에서 사용자 ID 추출
    public int getUserId(String token) {
        return Integer.parseInt(Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject());
    }
    
    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 유효하지 않은 토큰 (만료, 변조 등)
            return false;
        }
    }
}
