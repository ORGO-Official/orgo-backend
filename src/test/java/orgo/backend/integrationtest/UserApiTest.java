package orgo.backend.integrationtest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserApiTest extends IntegrationTest {
    private final static String GET_PROFILE_API = "/api/users/profile";
    private final static String UPDATE_PROFILE_API = "/api/users/profile";

    @Test
    @DisplayName("회원이 프로필을 조회한다. ")
    void getProfile() {
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.AUTH, accessToken)
                .when().get(GET_PROFILE_API)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @Test
    @DisplayName("회원이 프로필을 수정한다.  ")
    void updateProfile()  {
        String accessToken = getAccessToken();
        UserProfileDto.Request requestDto = new UserProfileDto.Request("수정할 닉네임");
        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.AUTH, accessToken)
                .multiPart("imageFile", new File("src/test/java/orgo/backend/setting/files/text-image.jpg"), MediaType.IMAGE_JPEG_VALUE)
                .multiPart("requestDto", requestDto, MediaType.APPLICATION_JSON_VALUE)
                .when().put(UPDATE_PROFILE_API)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }
}
