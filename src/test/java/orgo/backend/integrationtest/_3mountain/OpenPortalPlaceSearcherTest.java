package orgo.backend.integrationtest._3mountain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.application.placesearcher.OpenPortalPlaceSearcher;
import orgo.backend.domain._3mountain.domain.PlaceInfo;
import orgo.backend.setting.IntegrationTest;

import java.util.List;

@Slf4j
public class OpenPortalPlaceSearcherTest extends IntegrationTest {
    @Autowired
    OpenPortalPlaceSearcher openPortalPlaceSearcher;

    @Test
    @DisplayName("Tour API로 아차산을 검색한다.")
    void test() {
        // given

        // when
        List<PlaceInfo> placeInfoList = openPortalPlaceSearcher.searchByLocation(127.103764, 37.57149, 10000);
        log.info("{}", placeInfoList);

        // then
    }
}
