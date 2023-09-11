package orgo.backend.domain._3mountain.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import orgo.backend.domain._3mountain.dto.RestaurantDto;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.service.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.service.placesearcher.PlaceSearcher;
import orgo.backend.domain._4climbingRecord.dto.PlaceSearchCondition;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.ServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Slf4j
@ServiceTest
class RestaurantServiceTest {

    @InjectMocks
    RestaurantService restaurantService;


    @Mock
    MountainRepository mountainRepository;

    @Mock
    PlaceSearcher placeSearcher;

    @Mock
    PlaceLinkFinder placeLinkFinder;

    @Test
    @DisplayName("근처의 식당 목록을 조회한다. ")
    void getNearbyRestaurant() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> places = Lists.newArrayList(newPlaceInfo(1, ""));
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition(mountain.getLocation().getLatitude(), mountain.getLocation().getLongitude(), RestaurantService.RADIUS_M);

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(places).willReturn(List.of()).willReturn(List.of());

        // when
        restaurantService.findNearbyRestaurant(mountainId);

        // then
        verify(mountainRepository).findById(mountainId);
        verify(placeSearcher).searchByLocation(placeSearchCondition, 1);
    }

    @Test
    @DisplayName("식당 목록을 거리 순으로 오름차순 정렬한다. ")
    void sortByDistance() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> places = Lists.newArrayList(newPlaceInfo(2, "addr"), newPlaceInfo(1, "addr"), newPlaceInfo(3, "addr"));

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(places).willReturn(List.of()).willReturn(List.of());
        given(placeLinkFinder.findLink("addr")).willReturn("placeLink");

        // when
        List<RestaurantDto.Response> nearbyRestaurant = restaurantService.findNearbyRestaurant(mountainId);
        List<Double> distances = nearbyRestaurant.stream()
                .map(RestaurantDto.Response::getDistance)
                .toList();

        // then
        assertThat(distances).containsExactly(1D, 2D, 3D);
    }

    @Test
    @DisplayName("모든 식당은 상세 링크를 가지고 있다. ")
    void havePlaceLink() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> places = Lists.newArrayList(newPlaceInfo(1, "addr1"), newPlaceInfo(2, "addr2"), newPlaceInfo(3, "addr3"));

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(places).willReturn(List.of()).willReturn(List.of());
        given(placeLinkFinder.findLink("addr1")).willReturn("placeLink1");
        given(placeLinkFinder.findLink("addr2")).willReturn("placeLink2");
        given(placeLinkFinder.findLink("addr3")).willReturn("placeLink3");

        // when
        List<RestaurantDto.Response> nearbyRestaurant = restaurantService.findNearbyRestaurant(mountainId);
        List<String> externalLinks = nearbyRestaurant.stream()
                .map(RestaurantDto.Response::getExternalLink)
                .toList();

        // then
        assertThat(externalLinks).containsExactly("placeLink1", "placeLink2", "placeLink3");
    }

    @Test
    @DisplayName("상세 링크가 없는 식당은 포함하지 않는다. ")
    void notFindPlacesWithoutExternalLink() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> placeSet1 = Lists.newArrayList(newPlaceInfo(1, "addr1"), newPlaceInfo(2, "addr2"), newPlaceInfo(3, "addr1"), newPlaceInfo(4, "addr1"), newPlaceInfo(5, "addr1"), newPlaceInfo(6, "addr1"), newPlaceInfo(7, "addr1"), newPlaceInfo(8, "addr1"), newPlaceInfo(9, "addr1"), newPlaceInfo(10, "addr1"));
        List<PlaceInfo> placeSet2 = Lists.newArrayList(newPlaceInfo(11, "addr1"), newPlaceInfo(12, "addr1"), newPlaceInfo(13, "addr1"), newPlaceInfo(14, "addr1"), newPlaceInfo(15, "addr1"), newPlaceInfo(16, "addr1"), newPlaceInfo(17, "addr1"), newPlaceInfo(18, "addr1"), newPlaceInfo(19, "addr1"), newPlaceInfo(20, "addr1"));
        PlaceSearchCondition placeSearchCondition = new PlaceSearchCondition(mountain.getLocation().getLatitude(), mountain.getLocation().getLongitude(), RestaurantService.RADIUS_M);

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(placeSet1).willReturn(placeSet2);
        given(placeLinkFinder.findLink("addr1")).willReturn("placeLink1");
        given(placeLinkFinder.findLink("addr2")).willReturn(null);

        // when
        List<RestaurantDto.Response> nearbyRestaurant = restaurantService.findNearbyRestaurant(mountainId);
        log.info("{}", nearbyRestaurant);
        List<String> externalLinks = nearbyRestaurant.stream()
                .map(RestaurantDto.Response::getExternalLink)
                .toList();

        // then
        verify(placeSearcher, times(2)).searchByLocation(any(PlaceSearchCondition.class), any(int.class));
        assertThat(externalLinks).containsOnly("placeLink1");
    }


    @Test
    @DisplayName("검색 결과가 10개 이하라면 그것만 반환한다.")
    void lessThan10() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> places = Lists.newArrayList(newPlaceInfo(1, "addr"), newPlaceInfo(2, "addr"), newPlaceInfo(3, "addr"));

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(places).willReturn(List.of()).willReturn(List.of());
        given(placeLinkFinder.findLink("addr")).willReturn("placeLink");

        // when
        List<RestaurantDto.Response> nearbyRestaurant = restaurantService.findNearbyRestaurant(mountainId);

        // then
        assertThat(nearbyRestaurant).hasSize(3);
    }

    @Test
    @DisplayName("검색 결과가 10개 이상이면 10개만 반환한다.")
    void moreThan10() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        List<PlaceInfo> placeSet1 = Lists.newArrayList(newPlaceInfo(1, "addr"), newPlaceInfo(2, "addr"), newPlaceInfo(3, "addr"), newPlaceInfo(4, "addr"), newPlaceInfo(5, "addr"), newPlaceInfo(6, "addr"), newPlaceInfo(7, "addr"), newPlaceInfo(8, "addr"), newPlaceInfo(9, "addr"), newPlaceInfo(10, "addr"));
        List<PlaceInfo> placeSet2 = Lists.newArrayList(newPlaceInfo(11, "addr"), newPlaceInfo(12, "addr"), newPlaceInfo(13, "addr"), newPlaceInfo(14, "addr"), newPlaceInfo(15, "addr"), newPlaceInfo(16, "addr"), newPlaceInfo(17, "addr"), newPlaceInfo(18, "addr"), newPlaceInfo(19, "addr"), newPlaceInfo(20, "addr"));
        List<PlaceInfo> placeSet3 = Lists.newArrayList(newPlaceInfo(21, "addr"), newPlaceInfo(22, "addr"), newPlaceInfo(23, "addr"), newPlaceInfo(24, "addr"), newPlaceInfo(25, "addr"), newPlaceInfo(26, "addr"), newPlaceInfo(27, "addr"), newPlaceInfo(28, "addr"), newPlaceInfo(29, "addr"), newPlaceInfo(30, "addr"));

        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));
        given(placeSearcher.searchByLocation(any(PlaceSearchCondition.class), any(int.class))).willReturn(placeSet1).willReturn(placeSet2).willReturn(placeSet3);
        given(placeLinkFinder.findLink("addr")).willReturn("placeLink");

        // when
        List<RestaurantDto.Response> nearbyRestaurant = restaurantService.findNearbyRestaurant(mountainId);

        // then
        assertThat(nearbyRestaurant).hasSize(10);
    }



    private PlaceInfo newPlaceInfo(double distance, String address) {
        return new PlaceInfo("이름", address, distance, 1.1, 1.1, "contact", "url", null);
    }
}