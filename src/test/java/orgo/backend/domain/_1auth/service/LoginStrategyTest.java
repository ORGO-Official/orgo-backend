package orgo.backend.domain._1auth.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import orgo.backend.domain._1auth.service.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.NaverLoginStrategy;
import orgo.backend.domain._1auth.vo.PersonalData;

@Slf4j
@Disabled
@ActiveProfiles("test")
@SpringBootTest
public class LoginStrategyTest {

    @Autowired
    KakaoLoginStrategy kakaoLoginStrategy;

    @Autowired
    NaverLoginStrategy naverLoginStrategy;

    @Test
    @Disabled
    @DisplayName("카카오 토큰을 이용해 유저 프로필을 받아온다.")
    void test() {
        // given
        String socialToken = "qB_A9toNCiGEojLR542DPC3oC3tOPbYnyQe-a61HCj11GQAAAYn59h-a";

        // when
        PersonalData personalData = kakaoLoginStrategy.getPersonalData(socialToken);
        log.info("{}", personalData);

        // then
    }

    @Test
    @Disabled
    @DisplayName("네이버 토큰을 이용해 유저 프로필을 받아온다.")
    void test1() {
        // given
        String socialToken = "AAAAN4QvNrwEbjZ5USuBkgv0xDFWPRRuf-hjAPaWtt6tFtux5ktDDs9h7y9XR9ixbtJXes7Oc7Nk3t44KHEz0n2Bv6g";

        // when
        PersonalData personalData = naverLoginStrategy.getPersonalData(socialToken);
        log.info("{}", personalData);

        // then
    }

    @Test
    @DisplayName("네이버 토큰을 이용해 회원탈퇴한다.")
    void test2() {
        // given
        String socialToken = "AAAAN4QvNrwEbjZ5USuBkgv0xDFWPRRuf-hjAPaWtt6tFtux5ktDDs9h7y9XR9ixbtJXes7Oc7Nk3t44KHEz0n2Bv6g";

        // when
        naverLoginStrategy.unlink(socialToken);

        // then
    }
}
