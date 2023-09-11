package orgo.backend.domain._3mountain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.service.placesearcher.OpenPortalPlaceSearcher;
import orgo.backend.domain._4climbingRecord.dto.PlaceSearchCondition;

import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class OpenPortalPlaceSearcherTest {
    @Autowired
    OpenPortalPlaceSearcher openPortalPlaceSearcher;

    @Test
    @DisplayName("Tour API로 아차산을 검색한다.")
    void test() {
        // given
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition(37.57149, 127.103764, 10000);

        // when
        List<PlaceInfo> placeInfoList = openPortalPlaceSearcher.searchByLocation(placeSearchCondition, 1);
        log.info("{}", placeInfoList);

        // then
    }
}
