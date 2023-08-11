package orgo.backend.domain._1auth.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._1auth.domain.PersonalData;

import java.util.Objects;

@Component
public class NaverProfileExtractor implements ProfileExtractor {

    @Override
    public PersonalData getPersonalData(String socialToken) {
        String PROFILE_API = "https://openapi.naver.com/v1/nid/me";

        WebClient webClient = WebClient.create();
        ResponseData responseData = webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + socialToken)
                .retrieve()
                .bodyToMono(ResponseData.class)
                .block();

        Objects.requireNonNull(responseData).validate();
        return PersonalData.fromNaver(responseData);
    }

    @Getter
    public static class ResponseData {
        private String resultcode;
        private Response response;

        @Getter
        public static class Response {
            private String name;
            private String gender;
            private String email;
            private String birthyear;
            private String birthday;
            private String id;
        }

        public void validate() {
            if (this.resultcode.equals("200")) {
                return;
            }
            if (this.resultcode.equals("401")) {
                throw new RuntimeException("소셜 토큰이 만료되었습니다. 다시 로그인해주세요.");
            }
            throw new RuntimeException("클라이언트 오류입니다. 다시 시도해주세요.");
        }
    }
}
