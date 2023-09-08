package orgo.backend.domain._3mountain.service.placesearcher;

import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._4climbingRecord.dto.PlaceSearchCondition;

import java.util.List;

/**
 * 특정 조건으로 장소를 검색하는 역할을 수행하는 장소 검색기입니다.
 */
public interface PlaceSearcher {
    /**
     * 위도, 경도를 기준으로 특정 반경 이내에 위치한 장소를 검색합니다.
     *
     * @param placeSearchCondition    조회 조건 (위도, 경도, 반경, 페이지 번호)
     */
    List<PlaceInfo> searchByLocation(PlaceSearchCondition placeSearchCondition, int page);
}
