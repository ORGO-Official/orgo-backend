package orgo.backend.domain._1auth.application.loginstrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._1auth.domain.NaverTokenRequirement;
import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.domain._1auth.domain.SocialTokenRequirement;
import orgo.backend.domain._1auth.domain.SocialToken;

import java.util.Objects;

@Slf4j
public class NaverLoginStrategy implements LoginStrategy {
    @Value("${auth.naver.client-id}")
    private String CLIENT_ID;
    @Value("${auth.naver.client-secret}")
    private String CLIENT_SECRET;
    private final static String PROFILE_API = "https://openapi.naver.com/v1/nid/me";
    private final static String ISSUE_API = "https://nid.naver.com/oauth2.0/token";
    private final static String UNLINK_API = "https://nid.naver.com/oauth2.0/token";


    /**
     * 네이버 프로필 조회 API를 호출하여, 사용자의 개인 정보를 추출합니다.
     *
     * @param socialToken 네이버에서 발급한 토큰
     * @return 네이버 프로필 정보
     */
    @Override
    public PersonalData getPersonalData(String socialToken) {
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

    /**
     * 네이버 접근 토큰 발급 요청 API를 호출하여 접근 토큰을 발급합니다.
     *
     * @return 소셜 토큰
     */
    @Override
    public SocialToken createSocialToken(SocialTokenRequirement socialTokenRequirement) {
        WebClient webClient = WebClient.create();
        NaverTokenRequirement naverTokenRequirement = (NaverTokenRequirement) Objects.requireNonNull(socialTokenRequirement);
        IssueResponse response = webClient.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                        .path(ISSUE_API)
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("client_secret", CLIENT_SECRET)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("code", naverTokenRequirement.getCode())
                        .queryParam("state", naverTokenRequirement.getState())
                        .build())
                .retrieve()
                .bodyToMono(IssueResponse.class)
                .block();
        return Objects.requireNonNull(response).toData();
    }

    /**
     * 네이버 접근 토큰 재발급 API를 호출하여 유효한 소셜 토큰을 재발급합니다.
     *
     * @param refreshToken 소셜 리프레시 토큰
     * @return 재발급한 소셜 토큰
     */
    @Override
    public SocialToken reissueSocialToken(String refreshToken) {
        WebClient webClient = WebClient.create();
        ReissueResponse response = webClient.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                        .path(ISSUE_API)
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("client_secret", CLIENT_SECRET)
                        .queryParam("grant_type", "refresh_token ")
                        .queryParam("refresh_token", refreshToken)
                        .build())
                .retrieve()
                .bodyToMono(ReissueResponse.class)
                .block();
        return Objects.requireNonNull(response).toData(refreshToken);
    }

    /**
     * 네이버 로그인 연동 해제 API를 호출하여, 연결을 끊습니다.
     *
     * @param socialToken 서드파티 액세스 토큰
     */
    @Override
    public void unlink(String socialToken) {
        WebClient webClient = WebClient.create();
        webClient.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                        .path(UNLINK_API)
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("client_secret", CLIENT_SECRET)
                        .queryParam("access_token", socialToken)
                        .queryParam("grant_type", "delete")
                        .build())
                .retrieve()
                .bodyToMono(NaverProfile.class)
                .block();
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

    @Getter
    private static class IssueResponse {
        String access_token;
        String refresh_token;

        private SocialToken toData() {
            return new SocialToken(null, access_token, refresh_token);
        }
    }

    @Getter
    private static class ReissueResponse {
        String access_token;

        private SocialToken toData(String refresh_token) {
            return new SocialToken(null, access_token, refresh_token);
        }
    }
}
