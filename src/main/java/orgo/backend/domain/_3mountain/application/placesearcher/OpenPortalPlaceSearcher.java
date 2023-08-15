package orgo.backend.domain._3mountain.application.placesearcher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._3mountain.application.placelinkfinder.PlaceLinkFinder;
import orgo.backend.domain._3mountain.domain.PlaceInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 공공데이터포털에서 제공하는 API를 사용하는 장소 검색기입니다.
 */
@Primary
@Component
@RequiredArgsConstructor
public class OpenPortalPlaceSearcher implements PlaceSearcher {

    @Value("${auth.openapi.serviceKey}")
    String SERVICE_KEY;

    private final static String HTTPS = "https";
    private final static String HOST = "apis.data.go.kr";
    private final static String LOCATION_SEARCH_API = "/B551011/KorService1/locationBasedList1";
    private final static String RESTAURANT_CONTENT_TYPE = "39";

    /**
     * 'Tour API - 위치기반 관광정보조회'를 이용하여 장소 정보를 반환합니다.
     *
     * @param latitude  위도
     * @param longitude 경도
     * @param radius    반경
     * @return 장소 정보
     */
    @Override
    public List<PlaceInfo> searchByLocation(double latitude, double longitude, double radius) {
        WebClient webClient = WebClient.create();
        ResponseData[] responseData = webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .scheme(HTTPS)
                        .host(HOST)
                        .path(LOCATION_SEARCH_API)
                        .queryParam("numOfRows", 50)
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "IOS")
                        .queryParam("MobileApp", "orgo")
                        .queryParam("_type", "json")
                        .queryParam("mapX", String.valueOf(latitude))
                        .queryParam("mapY", String.valueOf(longitude))
                        .queryParam("radius", String.valueOf(radius))
                        .queryParam("contentTypeId", RESTAURANT_CONTENT_TYPE)
                        .queryParam("serviceKey", SERVICE_KEY)
                        .build())
                .retrieve()
                .bodyToMono(ResponseData[].class)
                .block();

        return Arrays.stream(Objects.requireNonNull(responseData))
                .map(PlaceInfo::fromOpenPortalPlaceSearcher)
                .toList();
    }

    @Getter
    public static class ResponseData {
        String title;
        String firstimage;
        String dist;
        String mapx;
        String mapy;
        String tel;
        String addr1;
    }
}
