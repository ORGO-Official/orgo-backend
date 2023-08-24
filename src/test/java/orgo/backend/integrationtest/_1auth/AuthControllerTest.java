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
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._etc.image.ImageUploader;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.TestJwtProvider;

import java.util.Collections;
import java.util.Optional;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsPathParam.pathParams;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static hansol.restdocsdsl.element.ParamElement.param;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AuthControllerTest extends IntegrationTest {

    private final static String LOGIN_API = "/api/auth/login/{loginType}";
    private final static String LOGOUT_API = "/api/auth/logout";
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
    @DisplayName("[소셜 로그인(네이버)] - 성공")
    void test() throws Exception {
        // given
        String socialToken = "social-access";
        PersonalData personalData = new PersonalData("김민수", "test@naver.com", "11111", LoginType.NAVER);
        given(naverLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "naver")
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

        assertThat(userRepository.findBySocialIdAndLoginType("11111", LoginType.NAVER)).isNotEmpty();
    }

    @Test
    @DisplayName("[소셜 로그인(카카오)] - 성공")
    void test1() throws Exception {
        // given
        String socialToken = "social-access";
        PersonalData personalData = new PersonalData("김민수", "test@kakao.com", "11111", LoginType.KAKAO);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "kakao")
                .header(Header.SOCIAL, socialToken));

        // then
        actions.andExpect(status().isOk());
        assertThat(userRepository.findBySocialIdAndLoginType("11111", LoginType.KAKAO)).isNotEmpty();
    }

    @Test
    @DisplayName("[회원 탈퇴(네이버)] - 성공")
    void test2() throws Exception {
        // given
        String socialToken = "social-access";
        User user = User.builder()
                .loginType(LoginType.NAVER)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        User saved = userRepository.save(user);


        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("auth-withdraw",
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 토큰(카카오/네이버/애플) \n\n 애플의 경우 \"{id}|{email}\"형식"),
                                header(Header.AUTH).description("액세스 토큰")
                        )));

        assertThat(userRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("[회원 탈퇴(카카오)] - 성공")
    void test3() throws Exception {
        // given
        String socialToken = "social-access";
        User user = User.builder()
                .loginType(LoginType.KAKAO)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        User saved = userRepository.save(user);

        // when
        ResultActions actions = mvc.perform(post(WITHDRAW_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isNoContent());
        assertThat(userRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("[로그아웃(네이버)] - 성공")
    void test4() throws Exception {
        // given
        String socialToken = "social-access";
        User user = User.builder()
                .loginType(LoginType.NAVER)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(user);

        // when
        ResultActions actions = mvc.perform(post(LOGOUT_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("auth-logout",
                        requestHeaders(
                                header(Header.SOCIAL).description("소셜 토큰(카카오/네이버/애플) \n\n 애플의 경우 \"{id}|{email}\"형식"),
                                header(Header.AUTH).description("액세스 토큰")
                        )));
    }

    @Test
    @DisplayName("[로그아웃(카카오)] - 성공")
    void test5() throws Exception {
        // given
        String socialToken = "social-access";
        User user = User.builder()
                .loginType(LoginType.KAKAO)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(user);

        // when
        ResultActions actions = mvc.perform(post(LOGOUT_API)
                .header(Header.SOCIAL, socialToken)
                .header(Header.AUTH, testJwtProvider.generate(user))
        );

        // then
        actions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("기존 회원 데이터가 존재하는 경우, 회원가입을 하지 않는다.")
    void test6() throws Exception {
        String socialToken = "social-access";
        User user = MockEntityFactory.mockUser();
        PersonalData personalData = new PersonalData(user.getNickname(), user.getEmail(), user.getSocialId(), user.getLoginType());
        given(naverLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);
        userRepository.save(user);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "naver")
                .header(Header.SOCIAL, socialToken));

        // then
        actions.andExpect(status().isOk());
        assertThat(userRepository.findBySocialIdAndLoginType(user.getSocialId(), user.getLoginType())).isNotEmpty();
    }

    @Test
    @DisplayName("회원가입에 성공하면, 기본 프로필 이미지로 설정된다.")
    void test7() throws Exception {
        //given
        String socialToken = "social-access";
        PersonalData personalData = new PersonalData("김민수", "test@kakao.com", "11111", LoginType.KAKAO);
        given(kakaoLoginStrategy.getPersonalData(socialToken)).willReturn(personalData);

        // when
        ResultActions actions = mvc.perform(post(LOGIN_API, "kakao")
                .header(Header.SOCIAL, socialToken));

        // then
        actions.andExpect(status().isOk());
        Optional<User> optionalUser = userRepository.findBySocialIdAndLoginType("11111", LoginType.KAKAO);
        assertThat(optionalUser).isNotEmpty();
        assertThat(optionalUser.get().getProfileImage()).contains(ImageUploader.DEFAULT_PROFILE_IMAGE_NAME);
    }
}
