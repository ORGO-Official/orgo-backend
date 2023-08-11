package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import orgo.backend.domain._1auth.application.NaverProfileExtractor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@Getter
public class PersonalData {
    String name;
    String email;
    LocalDate birthdate;
    String socialId;

    /**
     * 네이버의 프로필 조회 API로 얻은 데이터로 PersonalData를 생성합니다.
     *
     * @param responseData API 조회 결과 데이터
     * @return PersonalData
     */
    public static PersonalData fromNaver(NaverProfileExtractor.ResponseData responseData) {
        NaverProfileExtractor.ResponseData.Response response = responseData.getResponse();
        String birthMonthDay = response.getBirthday().replace("-", "");
        LocalDate birthDate = LocalDate.parse(response.getBirthyear() + birthMonthDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return PersonalData.builder()
                .name(response.getName())
                .email(response.getEmail())
                .birthdate(birthDate)
                .socialId(response.getId())
                .build();
    }
}
