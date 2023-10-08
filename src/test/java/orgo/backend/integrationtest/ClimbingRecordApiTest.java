package orgo.backend.integrationtest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ClimbingRecordApiTest extends IntegrationTest {
    private final static String REGISTER_CLIMBINGRECORDS_API = "/api/climbing-records";
    private final static String VIEW_CLIMBINGRECORDS_API = "/api/climbing-records";

    @Autowired
    MountainRepository mountainRepository;

//    @Test
//    @DisplayName("정확한 위치에서 산 완등을 인증한다. ")
//    void registerRecord() {
//        //given
//        long mountainId = 1L;
//        Mountain mountain = mountainRepository.findById(mountainId).get();
//
//        UserPosDto userPosDto = UserPosDto.builder()
//                .date(LocalDateTime.now())
//                .mountainId(mountain.getId())
//                .altitude(mountain.getLocation().getAltitude())
//                .latitude(mountain.getLocation().getLatitude())
//                .longitude(mountain.getLocation().getLongitude())
//                .build();
//        String accessToken = getAccessToken();
//
//        // when
//        ExtractableResponse<Response> response = RestAssured
//                .given().log().all()
//                .header(Header.AUTH, accessToken)
//                .body(userPosDto)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().post(REGISTER_CLIMBINGRECORDS_API)
//                .then().log().all()
//                .extract();
//
//        // then
//        assertAll(
//                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
//        );
//    }

    @Test
    @DisplayName("사용자의 완등 기록을 조회한다.")
    void viewRecords() {
        //given
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.AUTH, accessToken)
                .when().get(VIEW_CLIMBINGRECORDS_API)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }
}
