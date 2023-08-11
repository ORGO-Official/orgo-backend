package orgo.backend.domain._1auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import orgo.backend.domain._1auth.application.loginstrategy.LoginStrategy;
import orgo.backend.domain._1auth.application.loginstrategy.LoginStrategyFactory;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.domain._1auth.domain.ServiceToken;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.global.config.security.JwtProvider;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    LoginStrategyFactory loginStrategyFactory;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * 소셜 로그인합니다.
     *
     * @param socialToken 소셜 토큰
     * @param method      로그인 방식
     * @return 액세스 토큰, 리프레시 토큰
     */
    public ServiceToken login(String socialToken, String method) {
        LoginType loginType = LoginType.findBy(method);
        LoginStrategy strategy = loginStrategyFactory.findStrategy(loginType);
        PersonalData personalData = strategy.getPersonalData(socialToken);
        User user = createOrGetUser(personalData);
        return jwtProvider.createServiceToken(user);
    }

    /**
     * 소셜 아이디와 로그인 타입을 기준으로 해당 회원 정보가 있는지 조회합니다.
     * 조회 결과가 없는 경우, 신규 회원으로 간주하고 회원을 등록합니다.
     *
     * @param personalData 개인 정보 (소셜 프로필)
     * @return 회원
     */
    private User createOrGetUser(PersonalData personalData) {
        return userRepository.findBySocialIdAndLoginType(personalData.getSocialId(), personalData.getLoginType())
                .orElse(userRepository.save(User.signup(personalData)));
    }

    public void logout(ServiceToken token, Long userId) {

    }

    public void withdraw(ServiceToken token, Long userId) {

    }
}
