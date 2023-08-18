package orgo.backend.integrationtest._2user;

import hansol.restdocsdsl.element.FieldElement;
import hansol.restdocsdsl.element.HeaderElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.application.UserService;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.TestJwtProvider;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTest {
    private final String GET_PROFILE_API = "/api/users/profile";
    @Autowired
    TestJwtProvider testJwtProvider;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("[프로필 조회] - 성공")
    void test() throws Exception {
        // given
        User user = MockEntityFactory.mockUser();
        User saved = userRepository.save(user);

        // when
        ResultActions actions = mvc.perform(get(GET_PROFILE_API)
                .header(Header.AUTH, testJwtProvider.generate(saved)));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("user-profile-get",
                        requestHeaders(
                                header(Header.AUTH).description("")
                        ),
                        responseFields(
                                field("id").type(JsonFieldType.NUMBER).description("회원 아이디넘버"),
                                field("nickname").description("닉네임"),
                                field("email").description("이메일"),
                                field("profileImage").description("프로필 이미지"),
                                field("loginType").description("로그인 타입 (네이버: \"NAVER\" | 카카오: \"KAKAO\" | 애플: \"APPLE\")")
                        )));

    }
}
