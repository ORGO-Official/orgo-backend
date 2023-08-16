package orgo.backend.domain._3mountain.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.application.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.application.placesearcher.PlaceSearcher;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.domain._3mountain.domain.Location;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.domain._3mountain.domain.PlaceInfo;
import orgo.backend.domain._3mountain.dto.RestaurantDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final MountainRepository mountainRepository;
    private final PlaceSearcher placeSearcher;
    private final PlaceLinkFinder placeLinkFinder;

    private final static int RADIUS_M = 2000;

    /**
     * 해당 산 근처의 식당 목록을 조회합니다.
     * 산의 위도, 경도를 기준으로 RADIUS_M 미터 반경 이내에 위치한 식당을 조회합니다.
     *
     * @param mountainId 산 아이디넘버
     * @return 근처 식당 목록
     */
    public List<RestaurantDto.Response> findNearbyRestaurant(Long mountainId) {
        Mountain mountain = mountainRepository.findById(mountainId).orElseThrow(RuntimeException::new);
        Location location = mountain.getLocation();
        return placeSearcher.searchByLocation(location.getLatitude(), location.getLongitude(), RADIUS_M).stream()
                .map(info -> new RestaurantDto.Response(info, placeLinkFinder.find(info.getAddress())))
                .toList();
    }
}
