package orgo.backend.integrationtest._etc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.global.config.security.CustomUserDetailsService;
import orgo.backend.global.constant.Header;
import orgo.backend.global.error.ErrorCode;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.TestJwtProvider;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionTest extends IntegrationTest {
    private final static String WITHDRAW_API = "/api/auth/withdraw";

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    TestJwtProvider testJwtProvider;


    @Test
    @DisplayName("Jwt의 형식이 잘못된 경우 예외가 발생한다.")
    void test() throws Exception {
        // given
        String socialToken = "social-access";

        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, "invalid-jwt")
        );

        // then
        actions.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_JWT.getCode()));
    }

    @Test
    @DisplayName("Jwt에 포함된 유저 아이디 넘버에 해당하는 회원이 존재하지 않는 경우 예외가 발생한다.")
    void test1() throws Exception {
        // given
        String socialToken = "social-access";
        User user = User.builder()
                .id(1L)
                .loginType(LoginType.KAKAO)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ErrorCode.USER_NOT_FOUND.getCode()));
    }
}
