package orgo.backend.integrationtest._1auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._1auth.application.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.application.loginstrategy.NaverLoginStrategy;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.Gender;
import orgo.backend.setting.IntegrationTest;

import java.time.LocalDate;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsPathParam.pathParams;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static hansol.restdocsdsl.element.ParamElement.param;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends IntegrationTest {

    private final static String LOGIN_API = "/api/auth/login/{loginType}";
    @MockBean
    NaverLoginStrategy naverLoginStrategy;

    @MockBean
    KakaoLoginStrategy kakaoLoginStrategy;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("[소셜 로그인(네이버) - 성공]")
    void test() throws Exception {
        // given
        String socialToken = "test_social_token";
        PersonalData personalData = new PersonalData("김민수", "test@naver.com", Gender.MALE, LocalDate.of(1999, 3, 11), "11111", LoginType.NAVER);
        given(naverLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "naver")
                .header("socialToken", socialToken));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("auth-login",
                        requestHeaders(header("socialToken").description("소셜 로그인 토큰")),
                        pathParams(param("loginType").description("로그인 타입 (네이버: \"naver\" | 카카오: \"kakao\")")),
                        responseFields(
                                field("accessToken").description("액세스 토큰"),
                                field("refreshToken").description("리프레시 토큰")
                        )));

        assertThat(userRepository.findBySocialIdAndLoginType("11111", LoginType.NAVER)).isNotEmpty();
    }

    @Test
    @DisplayName("[소셜 로그인(카카오) - 성공]")
    void test1() throws Exception {
        // given
        String socialToken = "test_social_token";
        PersonalData personalData = new PersonalData("김민수", "test@kakao.com", Gender.MALE, LocalDate.of(1999, 3, 11), "11111", LoginType.KAKAO);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "kakao")
                .header("socialToken", socialToken));

        // then
        actions.andExpect(status().isOk());
        assertThat(userRepository.findBySocialIdAndLoginType("11111", LoginType.KAKAO)).isNotEmpty();
    }
}
