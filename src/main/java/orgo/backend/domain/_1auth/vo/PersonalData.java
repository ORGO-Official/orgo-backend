package orgo.backend.domain._1auth.vo;

import lombok.*;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._1auth.service.loginstrategy.AppleLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.NaverLoginStrategy;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PersonalData {
    private String nickname;
    private String email;
    private String socialId;
    private LoginType loginType;

    /**
     * 네이버의 프로필 조회 API로 얻은 데이터로 PersonalData를 생성합니다.
     *
     * @param naverProfile API 조회 결과 데이터
     * @return PersonalData
     */
    public static PersonalData fromNaver(NaverLoginStrategy.NaverProfile naverProfile) {
        NaverLoginStrategy.NaverProfile.Response response = naverProfile.getResponse();
        return PersonalData.builder()
                .nickname(response.getNickname())
                .email(response.getEmail())
                .socialId(response.getId())
                .loginType(LoginType.NAVER)
                .build();
    }

    public static PersonalData fromKakao(KakaoLoginStrategy.KakaoProfile kakaoProfile) {
        KakaoLoginStrategy.KakaoProfile.KakaoAccount kakaoAccount = kakaoProfile.getKakao_account();
        return PersonalData.builder()
                .nickname(kakaoAccount.getProfile().getNickname())
                .email(kakaoAccount.getEmail())
                .socialId(String.valueOf(kakaoProfile.getId()))
                .loginType(LoginType.KAKAO)
                .build();
    }

    public static PersonalData fromApple(AppleLoginStrategy.AppleProfile appleProfile) {
        return PersonalData.builder()
                .nickname("오르고")
                .email(appleProfile.getEmail())
                .socialId(String.valueOf(appleProfile.getId()))
                .loginType(LoginType.APPLE)
                .build();
    }
}
