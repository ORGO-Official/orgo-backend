package orgo.backend.domain._3mountain.application.placesearcher;

import orgo.backend.domain._3mountain.domain.PlaceInfo;

import java.util.List;

/**
 * 특정 조건으로 장소를 검색하는 역할을 수행하는 장소 검색기입니다.
 */
public interface PlaceSearcher {
    /**
     * 위도, 경도를 기준으로 특정 반경 이내에 위치한 장소를 검색합니다.
     *
     * @param latitude  위도
     * @param longitude 경도
     * @param radius    반경
     */
    List<PlaceInfo> searchByLocation(double latitude, double longitude, double radius);
}
