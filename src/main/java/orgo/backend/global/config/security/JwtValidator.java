package orgo.backend.global.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import orgo.backend.global.error.ErrorCode;
import orgo.backend.global.error.exception.jwtexception.CustomJwtException;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
public class JwtValidator {

    public static Claims validateJwt(String jwt, Key key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (SecurityException e) {
            log.error("잘못된 시그니처");
            throw new CustomJwtException(ErrorCode.WRONG_SIGNATURE_JWT);
        } catch (ExpiredJwtException e) {
            log.error("Jwt 만료");
            throw new CustomJwtException(ErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식");
            throw new CustomJwtException(ErrorCode.UNSUPPORTED_JWT);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 JWT 토큰");
            throw new CustomJwtException(ErrorCode.INVALID_JWT);
        }
    }
}
