package orgo.backend.domain._1auth.application.loginstrategy;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.domain._1auth.domain.SocialToken;
import orgo.backend.domain._1auth.domain.SocialTokenRequirement;

import java.util.Objects;

@Service
@Slf4j
@Transactional
public class KakaoLoginStrategy implements LoginStrategy{


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
        return null;
    }

    @Override
    public SocialToken reissueSocialToken(String refreshToken) {
        return null;
    }

    @Override
    public void unlink(String socialToken) {

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
}
