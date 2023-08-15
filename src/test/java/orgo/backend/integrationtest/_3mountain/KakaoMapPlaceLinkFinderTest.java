package orgo.backend.integrationtest._3mountain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.application.placelinkfinder.KakaoMapPlaceLinkFinder;
import orgo.backend.domain._3mountain.application.placesearcher.OpenPortalPlaceSearcher;
import orgo.backend.domain._3mountain.domain.PlaceInfo;
import orgo.backend.setting.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class KakaoMapPlaceLinkFinderTest extends IntegrationTest {
    @Autowired
    KakaoMapPlaceLinkFinder kakaoMapPlaceLinkFinder;

    @Test
    @DisplayName("주소를 기반으로 카카오맵 링크를 찾는다. ")
    void test() {
        // given
        String address = "서울특별시 중랑구 용마산로 389 석주빌딩";

        // when
        String link = kakaoMapPlaceLinkFinder.find(address);
        log.info("{}", link);

        // then
        assertThat(link).isNotBlank();
    }
}
