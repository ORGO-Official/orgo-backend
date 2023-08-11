package orgo.backend.domain._1auth.application.loginstrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._1auth.domain.PersonalData;

import java.util.Objects;

@Slf4j
public class NaverLoginStrategy implements LoginStrategy {

    /**
     * 네이버 프로필 조회 API를 호출하여, 사용자의 개인 정보를 추출합니다.
     *
     * @param socialToken 네이버에서 발급한 토큰
     * @return 네이버 프로필 정보
     */
    @Override
    public PersonalData getPersonalData(String socialToken) {
        String PROFILE_API = "https://openapi.naver.com/v1/nid/me";
        WebClient webClient = WebClient.create();
        NaverProfile naverProfile = webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + socialToken)
                .retrieve()
                .bodyToMono(NaverProfile.class)
                .block();

        Objects.requireNonNull(naverProfile).validate();
        return PersonalData.fromNaver(naverProfile);
    }

    @Getter
    @AllArgsConstructor
    public static class NaverProfile {
        private String resultcode;
        private Response response;

        @Getter
        @AllArgsConstructor
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
