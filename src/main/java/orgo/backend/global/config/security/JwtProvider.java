package orgo.backend.global.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import orgo.backend.domain._1auth.vo.ServiceToken;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.global.error.exception.JwtWrongFormatException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    public static final String AUTHORITIES = "authorities";
    public static final Long ACCESS_SEC = 31536000000L; // 1000 * 60 * 60 * 24 * 365 (1년)
    public static final Long REFRESH_SEC = 31536000000L; // 1000 * 60 * 60 * 24 * 365 (1년)

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(User user){
        return generateJwt(user, ACCESS_SEC);
    }

    public String createRefreshToken(User user){
        return generateJwt(user, REFRESH_SEC);
    }

    private String generateJwt(User user, Long expireTime) {
        Claims claims = injectValues(user, expireTime);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims injectValues(User user, Long expireTime) {
        long now = (new Date()).getTime();
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(user.getId()));
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(now + expireTime));
        claims.setId(UUID.randomUUID().toString());
        claims.put(AUTHORITIES, authorities);
        return claims;
    }

    public Claims parseToClaims(String jwt) throws ExpiredJwtException{
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (SecurityException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new JwtWrongFormatException();
        }
    }
}
