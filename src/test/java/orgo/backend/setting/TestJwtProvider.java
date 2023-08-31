package orgo.backend.setting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.global.config.security.JwtProvider;


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
