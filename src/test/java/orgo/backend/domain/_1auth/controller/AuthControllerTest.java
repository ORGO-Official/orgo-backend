package orgo.backend.domain._1auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._1auth.service.AuthService;
import orgo.backend.domain._1auth.vo.ServiceToken;
import orgo.backend.global.config.security.JwtAuthenticationFilter;
import orgo.backend.global.config.security.SecurityConfig;
import orgo.backend.global.constant.Header;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsPathParam.pathParams;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static hansol.restdocsdsl.element.ParamElement.param;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(value = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
public class AuthControllerTest {

    private final static String LOGIN_API = "/api/auth/login/{loginType}";
    private final static String LOGOUT_API = "/api/auth/logout";
    private final static String WITHDRAW_API = "/api/auth/withdraw";

    @MockBean
    AuthService authService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("[소셜 로그인] - 성공")
    void test() throws Exception {
        // given
        String socialToken = "social-access-token";
        LoginType loginType = LoginType.KAKAO;
        given(authService.login(socialToken, loginType.getName())).willReturn(new ServiceToken("access-token", "refresh-token"));

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, loginType.getName())
                .with(csrf())
                .header(Header.SOCIAL, socialToken));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("auth-login",
                        pathParams(param("loginType").description("로그인 타입 \n\n [네이버: \"naver\" | 카카오: \"kakao\" | 애플: \"apple\"]")),
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 토큰(카카오/네이버/애플) \n\n 애플의 경우 \"{id}|{email}\"형식")
                        ),
                        responseFields(
                                field("accessToken").description("액세스 토큰"),
                                field("refreshToken").description("리프레시 토큰")
                        )));
    }


    @Test
    @WithMockUser
    @DisplayName("[회원 탈퇴] - 성공")
    void test2() throws Exception {
        // given
        String socialToken = "social-access-token";
        String accessToken = "access-token";

        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .with(csrf())
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, accessToken)
        );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("auth-withdraw",
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 토큰(카카오/네이버/애플) \n\n 애플의 경우 \"{id}|{email}\"형식"),
                                header(Header.AUTH).description("액세스 토큰")
                        )));
    }


    @Test
    @WithMockUser
    @DisplayName("[로그아웃] - 성공")
    void test4() throws Exception {
        // given
        String socialToken = "social-access-token";
        String accessToken = "access-token";
        // when
        ResultActions actions = mvc.perform(post(LOGOUT_API)
                .with(csrf())
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, accessToken)
        );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("auth-logout",
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 토큰(카카오/네이버/애플) \n\n 애플의 경우 \"{id}|{email}\"형식"),
                                header(Header.AUTH).description("액세스 토큰")
                        )));
    }
}
