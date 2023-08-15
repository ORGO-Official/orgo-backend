package orgo.backend.domain._3mountain.application.placesearcher;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import orgo.backend.domain._3mountain.application.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.domain.PlaceInfo;

/**
 * 공공데이터포털에서 제공하는 API를 사용하는 장소 검색기입니다.
 */
@Component
@RequiredArgsConstructor
public class OpenPortalPlaceSearcher implements PlaceSearcher {

    @Autowired
    PlaceLinkFinder placeLinkFinder;

    /**
     * 'Tour API - 위치기반 관광정보조회'를 이용하여 장소 정보를 반환합니다.
     *
     * @param latitude  위도
     * @param longitude 경도
     * @param radius    반경
     * @return 장소 정보
     */
    @Override
    public PlaceInfo searchByLocation(double latitude, double longitude, double radius) {
        return null;
    }
}
