package orgo.backend.setting;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import orgo.backend.domain._1auth.domain.ServiceToken;
import orgo.backend.domain._2user.domain.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class TestJwtProvider {
    private final static String SECRET_KEY = "HelloMyNickNameIsBeHangAndNiceToMeetYouHowAreYouImFineThankYouAndYou2";

    public static String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(user.getId()));
        claims.put("authorities", authorities);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
