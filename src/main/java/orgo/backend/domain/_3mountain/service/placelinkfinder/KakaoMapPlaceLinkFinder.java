package orgo.backend.domain._3mountain.service.placelinkfinder;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Primary
@Component
public class KakaoMapPlaceLinkFinder implements PlaceLinkFinder {
    @Value("${auth.kakao.rest_api_key}")
    String REST_API_KEY;
    private final static String HTTPS = "https";
    private final static String HOST = "dapi.kakao.com";
    private final static String KEYWORD_SEARCH_API = "/v2/local/search/keyword";

    @Override
    public String find(String address) {
        WebClient webClient = WebClient.create();
        KakaoMapPlaceLinkFinder.ResponseData responseData = webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .scheme(HTTPS)
                        .host(HOST)
                        .path(KEYWORD_SEARCH_API)
                        .queryParam("query", address)
                        .queryParam("category_group_code", "FD6")
                        .build())
                .header("Authorization", "KakaoAK " + REST_API_KEY)
                .retrieve()
                .bodyToMono(KakaoMapPlaceLinkFinder.ResponseData.class)
                .block();
        List<ResponseData.Document> documents = Objects.requireNonNull(responseData).getDocuments();
        if (documents.isEmpty()) {
            return "";
        }
        return documents.get(0).getPlace_url();
    }

    @Getter
    public static class ResponseData {
        List<Document> documents;

        @Getter
        public static class Document {
            String place_url;
        }
    }
}
