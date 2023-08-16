package orgo.backend.domain._1auth.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final LoginStrategyFactory loginStrategyFactory;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * 소셜 로그인합니다.
     *
     * @param socialToken 소셜 토큰
     * @param method      로그인 방식
     * @return 액세스 토큰, 리프레시 토큰
     */
    @Transactional
    public ServiceToken login(String socialToken, String method) {
        LoginStrategy strategy = loginStrategyFactory.findStrategy(LoginType.findBy(method));
        PersonalData personalData = strategy.getPersonalData(socialToken);
        User user = createOrGetUser(personalData);
        return jwtProvider.createServiceToken(user);
    }

    /**
     * 소셜 아이디와 로그인 타입을 기준으로 해당 회원 정보가 있는지 조회합니다.
     * 조회 결과가 없는 경우, 신규 회원으로 간주하고 회원을 등록합니다.
     * 새로 발급한 소셜 토큰을 저장합니다.
     *
     * @param personalData 개인 정보 (소셜 프로필)
     * @return 회원
     */
    private User createOrGetUser(PersonalData personalData) {
        return userRepository.findBySocialIdAndLoginType(personalData.getSocialId(), personalData.getLoginType())
                .orElse(userRepository.save(User.signup(personalData)));
    }

    /**
     * 회원을 로그아웃시킵니다.
     * 연결된 소셜 계정의 토큰이 만료됩니다.
     *
     * @param socialToken 소셜 토큰
     * @param userId      회원 아이디넘버
     */
    public void logout(String socialToken, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        LoginStrategy strategy = loginStrategyFactory.findStrategy(user.getLoginType());
        strategy.logout(socialToken);
    }

    /**
     * 회원을 탈퇴시킵니다.
     *
     * @param socialToken 소셜 토큰
     * @param userId      회원 아이디넘버
     */
    @Transactional
    public void withdraw(String socialToken, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        LoginStrategy strategy = loginStrategyFactory.findStrategy(user.getLoginType());
        strategy.unlink(socialToken);
        userRepository.delete(user);
    }
}
