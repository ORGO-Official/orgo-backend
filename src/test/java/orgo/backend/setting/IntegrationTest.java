package orgo.backend.setting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._1auth.service.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.vo.PersonalData;
import orgo.backend.domain._1auth.vo.ServiceToken;
import orgo.backend.global.constant.Header;

import static org.mockito.BDDMockito.given;

@ActiveProfiles("inttest")
@TestExecutionListeners(value = TruncationTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private final static String LOGIN_API = "/api/auth/login/{loginType}";

    @LocalServerPort
    int port;


    @BeforeEach
    void init() {
        RestAssured.port = port;
    }

    @MockBean
    protected KakaoLoginStrategy kakaoLoginStrategy;

    protected String getAccessToken(){
        String socialToken = "social-token";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", LoginType.KAKAO);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, LoginType.KAKAO.getName())
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("accessToken");
    }

    protected ServiceToken getTokenSet(){
        String socialToken = "social-token";
        PersonalData personalData = new PersonalData("김철수", "chul@naver.com", "xyzabc", LoginType.KAKAO);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(Header.SOCIAL, socialToken)
                .when().post(LOGIN_API, LoginType.KAKAO.getName())
                .then().log().all()
                .extract();

        // then
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getObject("$", ServiceToken.class);
    }
}
