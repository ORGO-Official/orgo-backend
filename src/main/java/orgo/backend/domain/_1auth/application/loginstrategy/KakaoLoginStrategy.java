package orgo.backend.domain._1auth.application.loginstrategy;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._1auth.domain.*;

import java.util.Objects;

@Service
@Slf4j
@Transactional
public class KakaoLoginStrategy implements LoginStrategy{
    @Value("${auth.kakao.client_id}")
    private String CLIENT_ID;
    @Value("${auth.kakao.client_secret}")
    private String CLIENT_SECRET;
    private final static String PROFILE_API = "https://kapi.kakao.com/v2/user/me";
    private final static String ISSUE_API = "https://kauth.kakao.com/oauth/token";
    private final static String UNLINK_API = "https://kapi.kakao.com/v1/user/unlink";

    /**
     * 카카오 프로필 조회 API를 호출하여, 사용자의 개인 정보를 추출합니다.
     *
     * @param socialToken 카카오에서 발급한 토큰
     * @return 카카오 프로필 정보
     */
    @Override
    public PersonalData getPersonalData(String socialToken) {
        final String PROFILE_API = "https://kapi.kakao.com/v2/user/me";

        WebClient webClient = WebClient.create();
        KakaoProfile kakaoProfile = webClient.method(HttpMethod.POST)
                .uri(PROFILE_API)
                .header("Authorization", "Bearer " + socialToken)
                .retrieve()
                .bodyToMono(KakaoProfile.class)
                .block();
        Objects.requireNonNull(kakaoProfile).validate();
        return PersonalData.fromKakao(kakaoProfile);
    }

    @Override
    public SocialToken createSocialToken(SocialTokenRequirement socialTokenRequirement) {
        WebClient webClient = WebClient.create();
        KakaoTokenRequirement kakaoTokenRequirement = (KakaoTokenRequirement) Objects.requireNonNull(socialTokenRequirement);
        KakaoLoginStrategy.IssueResponse response = webClient.method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                        .path(ISSUE_API)
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("client_secret", CLIENT_SECRET)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("code", kakaoTokenRequirement.getCode())
                        .queryParam("redirect_uri", kakaoTokenRequirement.getRedirectUri())
                        .build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(KakaoLoginStrategy.IssueResponse.class)
                .block();
        return Objects.requireNonNull(response).toData();
    }

    @Override
    public SocialToken reissueSocialToken(String refreshToken) {
        return null;
    }

    @Override
    public void unlink(String socialToken) {
        WebClient webClient = WebClient.create();
        webClient.method(HttpMethod.POST)
                .uri(UNLINK_API)
                .header("Authorization", "Bearer " + socialToken)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoProfile {
        private long id;
        private KakaoAccount kakao_account;

        @Getter
        public static class KakaoAccount {
            private String name;
            private String email;
            private String birthyear;
            private String birthday;
            private String gender;
            private Profile profile;
            @Getter
            public static class Profile {
                private String nickname;
            }
        }
        public void validate(){

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
