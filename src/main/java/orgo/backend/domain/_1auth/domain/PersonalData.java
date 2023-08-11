package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
     * @param responseData API 조회 결과 데이터
     * @return PersonalData
     */
    public static PersonalData fromNaver(NaverLoginStrategy.ResponseData responseData) {
        NaverLoginStrategy.ResponseData.Response response = responseData.getResponse();
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
}
