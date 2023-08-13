package orgo.backend.domain._1auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import orgo.backend.domain._1auth.application.loginstrategy.LoginStrategy;
import orgo.backend.domain._1auth.application.loginstrategy.LoginStrategyFactory;
import orgo.backend.domain._1auth.domain.*;
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
     * @param socialTokenRequirement 소셜 토큰 발급을 위한 필드
     * @param method                 로그인 방식
     * @return 액세스 토큰, 리프레시 토큰
     */
    public ServiceToken login(SocialTokenRequirement socialTokenRequirement, String method) {
        LoginType loginType = LoginType.findBy(method);
        LoginStrategy strategy = loginStrategyFactory.findStrategy(loginType);
        SocialToken socialToken = strategy.createSocialToken(socialTokenRequirement);
        PersonalData personalData = strategy.getPersonalData(socialToken.getAccessToken());
        User user = createOrGetUser(personalData, socialToken);
        return jwtProvider.createServiceToken(user);
    }

    /**
     * 소셜 아이디와 로그인 타입을 기준으로 해당 회원 정보가 있는지 조회합니다.
     * 조회 결과가 없는 경우, 신규 회원으로 간주하고 회원을 등록합니다.
     * 새로 발급한 소셜 토큰을 저장합니다.
     *
     * @param personalData 개인 정보 (소셜 프로필)
     * @param socialToken  소셜 토큰
     * @return 회원
     */
    private User createOrGetUser(PersonalData personalData, SocialToken socialToken) {
        User user = userRepository.findBySocialIdAndLoginType(personalData.getSocialId(), personalData.getLoginType())
                .orElse(User.signup(personalData));
        user.setSocialToken(socialToken);
        return userRepository.save(user);
    }

    public void logout(Long userId) {

    }

    /**
     * 회원을 탈퇴시킵니다.
     *
     * @param userId 회원 아이디넘버
     */
    public void withdraw(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        LoginType loginType = user.getLoginType();
        LoginStrategy strategy = loginStrategyFactory.findStrategy(loginType);
        String socialAccessToken = user.getSocialToken().getAccessToken();
        strategy.unlink(socialAccessToken);
        userRepository.delete(user);
    }
}
