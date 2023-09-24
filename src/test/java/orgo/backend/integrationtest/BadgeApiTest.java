package orgo.backend.integrationtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BadgeApiTest extends IntegrationTest {

    private final static String GET_ACQUIRED_BADGES = "/api/badges/acquired";
    private final static String GET_NOT_ACQUIRED_BADGES = "/api/badges/not-acquired";

    @Test
    @DisplayName("획득한 뱃지 목록을 조회한다.")
    void getAcquiredBadges(){
        //given

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.AUTH, getAccessToken())
                .when().get(GET_ACQUIRED_BADGES)
                .then().log().all()
                .extract();

        //then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getList("$", BadgeDto.Acquired.class)).isEmpty()
        );
    }

    @Test
    @DisplayName("획득하지 못한 뱃지 목록을 조회한다.")
    void getNotAcquiredBadges(){
        //given

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.AUTH, getAccessToken())
                .when().get(GET_NOT_ACQUIRED_BADGES)
                .then().log().all()
                .extract();

        //then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getList("$", BadgeDto.NotAcquired.class)).isNotEmpty()
        );

    }
}
