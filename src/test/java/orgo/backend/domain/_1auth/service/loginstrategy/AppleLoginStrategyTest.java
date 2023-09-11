package orgo.backend.domain._1auth.service.loginstrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orgo.backend.domain._1auth.vo.PersonalData;

import static org.assertj.core.api.Assertions.assertThat;

class AppleLoginStrategyTest {
    @Test
    @DisplayName("")
    void test() {
        // given
        AppleLoginStrategy appleLoginStrategy = new AppleLoginStrategy();
        String socialToken = "abcdefg|test1234@naver.com";

        // when
        PersonalData personalData = appleLoginStrategy.getPersonalData(socialToken);

        // then
        assertThat(personalData.getSocialId()).isEqualTo("abcdefg");
        assertThat(personalData.getEmail()).isEqualTo("test1234@naver.com");
    }
}