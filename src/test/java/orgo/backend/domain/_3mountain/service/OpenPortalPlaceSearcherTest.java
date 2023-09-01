package orgo.backend.domain._3mountain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.service.placesearcher.OpenPortalPlaceSearcher;

import java.util.List;

@Slf4j
@SpringBootTest
public class OpenPortalPlaceSearcherTest {
    @Autowired
    OpenPortalPlaceSearcher openPortalPlaceSearcher;

    @Test
    @DisplayName("Tour API로 아차산을 검색한다.")
    void test() {
        // given

        // when
        List<PlaceInfo> placeInfoList = openPortalPlaceSearcher.searchByLocation(37.57149, 127.103764, 10000);
        log.info("{}", placeInfoList);

        // then
    }
}
