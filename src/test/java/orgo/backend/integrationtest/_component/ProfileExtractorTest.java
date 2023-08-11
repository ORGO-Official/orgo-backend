package orgo.backend.integrationtest._component;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._1auth.application.NaverProfileExtractor;
import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.setting.IntegrationTest;

public class ProfileExtractorTest extends IntegrationTest {

    @Autowired
    NaverProfileExtractor naverProfileExtractor;

    @Test
    @Disabled
    @DisplayName("사용자의 네이버 프로필을 조회하여 개인 정보를 추출한다. ")
    void test() {
        // given

        // when
        PersonalData personalData = naverProfileExtractor.getPersonalData("");

        // then
        Assertions.assertThat(personalData.getSocialId()).isNotNull();
    }

}
