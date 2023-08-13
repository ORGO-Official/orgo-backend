package orgo.backend.integrationtest._1auth;

import lombok.extern.slf4j.Slf4j;
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
import orgo.backend.domain._2user.domain.User;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.TestJwtProvider;

import java.time.LocalDate;
import java.util.Collections;

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

@Slf4j
public class AuthControllerTest extends IntegrationTest {

    private final static String LOGIN_API = "/api/auth/login/{loginType}";
    private final static String WITHDRAW_API = "/api/auth/withdraw";
    @MockBean
    NaverLoginStrategy naverLoginStrategy;

    @MockBean
    KakaoLoginStrategy kakaoLoginStrategy;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestJwtProvider testJwtProvider;

    @Test
    @DisplayName("[소셜 로그인(네이버) - 성공]")
    void test() throws Exception {
        // given
        String socialToken = "test_social_token";
        PersonalData personalData = new PersonalData("김민수", "test@naver.com", Gender.MALE, LocalDate.of(1999, 3, 11), "11111", LoginType.NAVER);
        given(naverLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "naver")
                .header(Header.SOCIAL, socialToken));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("auth-login",
                        requestHeaders(header(Header.SOCIAL).description("소셜 로그인 토큰")),
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

    @Test
    @DisplayName("[회원탈퇴(네이버 기반 유저) - 성공]")
    void test2() throws Exception {
        // given
        User user = User.builder()
                .loginType(LoginType.NAVER)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        User saved = userRepository.save(user);

        String socialToken = "test_social_token";

        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("auth-withdraw",
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 로그인 토큰"),
                                header(Header.AUTH).description("액세스 토큰")
                        )));


        assertThat(userRepository.findById(saved.getId())).isEmpty();
    }
}
