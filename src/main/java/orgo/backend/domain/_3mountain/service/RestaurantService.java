package orgo.backend.domain._3mountain.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.service.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.service.placesearcher.PlaceSearcher;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.entity.Location;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.dto.RestaurantDto;
import orgo.backend.domain._4climbingRecord.dto.PlaceSearchCondition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final MountainRepository mountainRepository;
    private final PlaceSearcher placeSearcher;
    private final PlaceLinkFinder placeLinkFinder;

    public final static int RADIUS_M = 5000;
    public final static int RESTAURANT_RETURN_SIZE = 10;

    /**
     * 해당 산 근처의 식당 목록을 조회합니다.
     * 산의 위도, 경도를 기준으로 RADIUS_M 미터 반경 이내에 위치한 식당을 조회합니다.
     *
     * @param mountainId 산 아이디넘버
     * @return 근처 식당 목록
     */
    public List<RestaurantDto.Response> findNearbyRestaurant(Long mountainId) {
        Mountain mountain = mountainRepository.findById(mountainId).orElseThrow(RuntimeException::new);
        List<PlaceInfo> restaurants = getRestaurants(mountain.getLocation());
        sortByDistanceDesc(restaurants);
        return mapToResponse(restaurants);
    }

    private List<PlaceInfo> getRestaurants(Location location) {
        List<PlaceInfo> result = new ArrayList<>();
        int count = 0;
        while (result.size() < RESTAURANT_RETURN_SIZE && count < 3) {
            List<PlaceInfo> places = placeSearcher.searchByLocation(new PlaceSearchCondition(location.getLatitude(), location.getLongitude(), RADIUS_M), count++);
            List<PlaceInfo> placesWithLink = findPlacesWithLink(places);
            int remainingNumber = RESTAURANT_RETURN_SIZE - result.size();
            for (int i = 0; i < Math.min(placesWithLink.size(), remainingNumber); i++) {
                result.add(placesWithLink.get(i));
            }
        }
        return result;
    }

    @NotNull
    private List<PlaceInfo> findPlacesWithLink(List<PlaceInfo> places) {
        setAllExternalLinks(places);
        return places.stream()
                .filter(PlaceInfo::hasLink)
                .toList();
    }

    private void setAllExternalLinks(List<PlaceInfo> places) {
        for (PlaceInfo place : places) {
            place.setExternalLink(placeLinkFinder.findLink(place.getAddress()));
        }
    }

    private void sortByDistanceDesc(List<PlaceInfo> places) {
        places.sort(Comparator.comparing(PlaceInfo::getDistance));
    }

    private List<RestaurantDto.Response> mapToResponse(List<PlaceInfo> places) {
        return places.stream()
                .map(RestaurantDto.Response::new)
                .toList();
    }
}
