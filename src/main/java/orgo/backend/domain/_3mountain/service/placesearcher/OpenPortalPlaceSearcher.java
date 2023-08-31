package orgo.backend.domain._3mountain.service.placesearcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import orgo.backend.domain._3mountain.entity.PlaceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 공공데이터포털에서 제공하는 API를 사용하는 장소 검색기입니다.
 */
@Primary
@Slf4j
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
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("localhost:8080");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient wc = WebClient.builder().
                uriBuilderFactory(factory)
                .baseUrl("localhost:8080")
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))).build())
                .build();

        ResponseFormat responseFormat = wc.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .scheme(HTTPS)
                        .host(HOST)
                        .path(LOCATION_SEARCH_API)
                        .queryParam("numOfRows", 50)
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "IOS")
                        .queryParam("MobileApp", "orgo")
                        .queryParam("_type", "json")
                        .queryParam("arrange", "R")
                        .queryParam("mapX", String.valueOf(longitude))
                        .queryParam("mapY", String.valueOf(latitude))
                        .queryParam("radius", String.valueOf(radius))
                        .queryParam("contentTypeId", RESTAURANT_CONTENT_TYPE)
                        .queryParam("serviceKey", SERVICE_KEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseFormat.class)
                .block();
        ResponseFormat.Response.Body.Items items = Objects.requireNonNull(responseFormat).getResponse().getBody().getItems();
        if (items == null) {
            return new ArrayList<>();
        }
        return items.getItem().stream()
                .map(PlaceInfo::fromOpenPortalPlaceSearcher)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @ToString
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseFormat {
        private Response response;

        @ToString
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            private Body body;

            @ToString
            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Body {
                private Items items;

                @ToString
                @Getter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Items {
                    private List<Item> item;

                    @ToString
                    @Getter
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Item {
                        private String title;
                        private String firstimage;
                        private String dist;
                        private String mapx;
                        private String mapy;
                        private String tel;
                        private String addr1;
                    }
                }
            }
        }
    }


}
