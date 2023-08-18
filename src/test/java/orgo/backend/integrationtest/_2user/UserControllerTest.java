package orgo.backend.integrationtest._2user;

import hansol.restdocsdsl.element.FieldElement;
import hansol.restdocsdsl.element.HeaderElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.application.UserService;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.TestJwtProvider;

import java.util.Optional;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsRequest.requestFields;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTest {
    private final String GET_PROFILE_API = "/api/users/profile";
    private final String UPDATE_PROFILE_API = "/api/users/profile";
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
                                header(Header.AUTH).description("액세스 토큰")
                        ),
                        responseFields(
                                field("id").type(JsonFieldType.NUMBER).description("회원 아이디넘버"),
                                field("nickname").description("닉네임"),
                                field("email").description("이메일"),
                                field("profileImage").description("프로필 이미지"),
                                field("loginType").description("로그인 타입 (네이버: \"NAVER\" | 카카오: \"KAKAO\" | 애플: \"APPLE\")")
                        )));

    }

    @Test
    @DisplayName("[프로필 수정] - 성공")
    void test1() throws Exception {
        // given
        User user = MockEntityFactory.mockUser();
        User saved = userRepository.save(user);
        UserProfileDto.Request requestDto = new UserProfileDto.Request("수정할 닉네임", "수정할 프로필 사진");

        // when
        ResultActions actions = mvc.perform(put(UPDATE_PROFILE_API)
                .header(Header.AUTH, testJwtProvider.generate(saved))
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("user-profile-update",
                        requestHeaders(
                                header(Header.AUTH).description("액세스 토큰")
                        ),
                        requestFields(
                                field("nickname").description("닉네임").optional(),
                                field("profileImage").description("프로필 이미지").optional()
                        )));
        Optional<User> optionalUser = userRepository.findById(saved.getId());
        assertThat(optionalUser).isNotEmpty();
        assertThat(optionalUser.get().getNickname()).isEqualTo("수정할 닉네임");
    }
}
