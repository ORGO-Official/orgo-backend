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
import orgo.backend.global.config.security.JwtProvider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class TestJwtProvider {
    private final JwtProvider jwtProvider;

    /**
     * 테스트용 JWT(액세스 토큰)을 리턴합니다.
     *
     * @param user 사용자
     * @return 테스트용 JWT
     */
    public String generate(User user) {
        return jwtProvider.createServiceToken(user).getAccessToken();
    }
}
