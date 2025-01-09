package pl.umcs.medlai.configure;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    private final Key key;

    @Autowired
    public JWTUtil(@Value("${JWT_KEY}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(Map<String, Object> claims, long expirationInMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired JWT", e);
        }
    }
}