package orgo.backend.integrationtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import orgo.backend.domain._3mountain.dto.MountainDto;
import orgo.backend.setting.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
public class MountainApiTest extends IntegrationTest {

    private final static String GET_ALL_API = "/api/mountains";
    private final static String GET_RESTAURANT_API = "/api/mountains/{mountainId}/restaurants";

    @Test
    @DisplayName("전체 산 목록을 조회한다. ")
    void getAllMountains() {
        // given

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get(GET_ALL_API)
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getList("", MountainDto.Response.class)).hasSize(10)
        );

    }

    @Test
    @DisplayName("특정 키워드로 검색한다. ")
    void searchMountain() {
        // given

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .queryParam("keyword", "아차산")
                .when().get(GET_ALL_API)
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getList("", MountainDto.Response.class)).hasSize(1)
        );
    }
}
