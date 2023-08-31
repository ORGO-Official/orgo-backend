package orgo.backend.domain._3mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.service.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.service.placesearcher.PlaceSearcher;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.entity.Location;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.dto.RestaurantDto;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final MountainRepository mountainRepository;
    private final PlaceSearcher placeSearcher;
    private final PlaceLinkFinder placeLinkFinder;

    private final static int RADIUS_M = 5000;

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
        List<PlaceInfo> places = placeSearcher.searchByLocation(location.getLatitude(), location.getLongitude(), RADIUS_M);
        sortPlacesByDistanceDesc(places);
        return pick10Places(places);
    }

    private void sortPlacesByDistanceDesc(List<PlaceInfo> places) {
        places.sort(Comparator.comparing(PlaceInfo::getDistance));
    }

    private List<RestaurantDto.Response> pick10Places(List<PlaceInfo> places) {
        return places.stream()
                .map(info -> new RestaurantDto.Response(info, placeLinkFinder.find(info.getAddress())))
                .limit(10L)
                .toList();
    }
}
