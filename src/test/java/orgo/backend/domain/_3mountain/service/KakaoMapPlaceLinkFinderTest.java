package orgo.backend.domain._3mountain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import orgo.backend.domain._3mountain.service.placelinkfinder.KakaoMapPlaceLinkFinder;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class KakaoMapPlaceLinkFinderTest {
    @Autowired
    KakaoMapPlaceLinkFinder kakaoMapPlaceLinkFinder;

    @Test
    @DisplayName("주소를 기반으로 카카오맵 링크를 찾는다. ")
    void test() {
        // given
        String address = "서울특별시 중랑구 용마산로 389 석주빌딩";

        // when
        String link = kakaoMapPlaceLinkFinder.findLink(address);
        log.info("{}", link);

        // then
        assertThat(link).isNotBlank();
    }
}
