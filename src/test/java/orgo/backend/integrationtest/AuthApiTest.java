package orgo.backend.integrationtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._1auth.service.loginstrategy.AppleLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.NaverLoginStrategy;
import orgo.backend.domain._1auth.vo.PersonalData;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;


@IntegrationTest
public class AuthApiTest {
    private final static String LOGIN_API = "/api/auth/login/{loginType}";
    private final static String LOGOUT_API = "/api/auth/logout";
    private final static String WITHDRAW_API = "/api/auth/withdraw";

    @MockBean
    KakaoLoginStrategy kakaoLoginStrategy;

    @MockBean
    NaverLoginStrategy naverLoginStrategy;

    @MockBean
    AppleLoginStrategy appleLoginStrategy;

    @LocalServerPort
    int port;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }


    @Test
    @DisplayName("서비스에 카카오로 로그인한다. ")
    void loginKakao() {
        // given
        String socialToken = "social-token";
        String loginType = "kakao";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", LoginType.findBy(loginType));
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, loginType)
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getString("accessToken")).isNotEmpty(),
                () -> assertThat(jsonPath.getString("refreshToken")).isNotEmpty()
        );
    }

    @Test
    @DisplayName("서비스에 네이버로 로그인한다. ")
    void loginNaver() {
        // given
        String socialToken = "social-token";
        String loginType = "naver";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", LoginType.findBy(loginType));
        given(naverLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, loginType)
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getString("accessToken")).isNotEmpty(),
                () -> assertThat(jsonPath.getString("refreshToken")).isNotEmpty()
        );
    }

    @Test
    @DisplayName("서비스에 애플로 로그인한다. ")
    void loginApple() {
        // given
        String socialToken = "social-token";
        String loginType = "apple";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", LoginType.findBy(loginType));
        given(appleLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, loginType)
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(jsonPath.getString("accessToken")).isNotEmpty(),
                () -> assertThat(jsonPath.getString("refreshToken")).isNotEmpty()
        );
    }

    @Test
    @DisplayName("서비스에서 로그아웃한다. ")
    void logout() {
        // given
        String socialToken = "social-token";
        String accessToken = getAccessToken(LoginType.KAKAO);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, accessToken)
                .when().post(LOGOUT_API)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    @DisplayName("서비스에서 회원탈퇴한다. ")
    void withdraw() {
        // given
        String socialToken = "social-token";
        String accessToken = getAccessToken(LoginType.KAKAO);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, accessToken)
                .when().post(WITHDRAW_API)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    public String getAccessToken(LoginType loginType){
        String socialToken = "social-token";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", loginType);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, loginType.getName())
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("accessToken");
    }
}
