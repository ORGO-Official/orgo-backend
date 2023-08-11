package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import orgo.backend.domain._1auth.application.KakaoLoginStrategy;
import orgo.backend.domain._1auth.application.NaverLoginStrategy;
import orgo.backend.domain._2user.domain.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
public class PersonalData {
    String name;
    String email;
    Gender gender;
    LocalDate birthdate;
    String socialId;
    LoginType loginType;

    /**
     * 네이버의 프로필 조회 API로 얻은 데이터로 PersonalData를 생성합니다.
     *
     * @param naverProfile API 조회 결과 데이터
     * @return PersonalData
     */
    public static PersonalData fromNaver(NaverLoginStrategy.NaverProfile naverProfile) {
        NaverLoginStrategy.NaverProfile.Response response = naverProfile.getResponse();
        String birthMonthDay = response.getBirthday().replace("-", "");
        LocalDate birthDate = LocalDate.parse(response.getBirthyear() + birthMonthDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return PersonalData.builder()
                .name(response.getName())
                .gender(Gender.findBy(LoginType.NAVER, response.getGender()))
                .email(response.getEmail())
                .birthdate(birthDate)
                .socialId(response.getId())
                .loginType(LoginType.NAVER)
                .build();
    }

    public static PersonalData fromKakao(KakaoLoginStrategy.KakaoProfile kakaoProfile) {
        KakaoLoginStrategy.KakaoProfile.KakaoAccount kakaoAccount = kakaoProfile.getKakao_account();
        LocalDate birthDate = LocalDate.parse(kakaoAccount.getBirthyear() + kakaoAccount.getBirthday(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return PersonalData.builder()
                .name(kakaoAccount.getName())
                .gender(Gender.findBy(LoginType.KAKAO, kakaoAccount.getGender()))
                .email(kakaoAccount.getEmail())
                .birthdate(birthDate)
                .socialId(String.valueOf(kakaoProfile.getId()))
                .loginType(LoginType.KAKAO)
                .build();
    }
}
