package orgo.backend.domain._1auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.global.config.security.JwtProvider;
import orgo.backend.global.error.exception.RefreshTokenExpiredException;
import orgo.backend.global.error.exception.ReissueFailedException;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtProvider jwtProvider;

    /**
     * 액세스 토큰을 재발급합니다.
     * 리프레시 토큰이 만료되지 않은 경우에만 재발급할 수 있습니다.
     *
     * @param accessToken  액세스 토큰
     * @param refreshToken 리프레시 토큰
     * @return 재발급한 액세스 토큰
     */
    public String reissueAccessToken(String accessToken, String refreshToken) {
        if (isReissueAvailable(accessToken, refreshToken)) {
            Claims claims = jwtProvider.parseToClaims(accessToken);
            User user = (User) jwtProvider.getAuthentication(claims).getPrincipal();
            return jwtProvider.createAccessToken(user);
        }
        throw new ReissueFailedException();
    }

    /**
     * 액세스 토큰 재발급이 가능한지 확인합니다.
     * 액세스 토큰의 subject와 리프레시 토큰의 subject의 동일 여부로 재발급 가능을 판단합니다.
     * 리프레시 토큰이 만료된 경우, RefreshTokenExpiredException를 던지며 클라이언트에게 재로그인을 유도합니다.
     *
     * @param accessToken  액세스 토큰
     * @param refreshToken 리프레시 토큰
     * @return 재발급 가능 여부
     */
    private boolean isReissueAvailable(String accessToken, String refreshToken) {
        String accessTokenSubject = jwtProvider.parseToClaims(accessToken).getSubject();
        try {
            String refreshTokenSubject = jwtProvider.parseToClaims(refreshToken).getSubject();
            return accessTokenSubject.equals(refreshTokenSubject);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        }
    }
}
