package orgo.backend.domain._2user.controller;

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
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.service.UserService;
import orgo.backend.global.config.security.JwtAuthenticationFilter;
import orgo.backend.global.config.security.SecurityConfig;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.WithCustomMockUser;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsRequestPart.requestParts;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static hansol.restdocsdsl.element.PartElement.part;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(value = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
public class UserControllerTest {
    private final static String GET_PROFILE_API = "/api/users/profile";
    private final static String UPDATE_PROFILE_API = "/api/users/profile";
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser(userId = "1")
    @DisplayName("[프로필 조회] - 성공")
    void test() throws Exception {
        // given
        String accessToken = "access-token";
        User user = MockEntityFactory.mockUser(1L);
        given(userService.getProfile(user.getId())).willReturn(new UserProfileDto.Response(user));

        // when
        ResultActions actions = mvc.perform(get(GET_PROFILE_API)
                .with(csrf())
                .header(Header.AUTH, accessToken));

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
    @WithCustomMockUser
    @DisplayName("[프로필 수정] - 성공")
    void test1() throws Exception {
        // given
        String accessToken = "access-token";
        UserProfileDto.Request requestDto = new UserProfileDto.Request("수정할 닉네임");
        MockMultipartFile requestPartImage = MockEntityFactory.mockMultipartFileImage("imageFile");
        MockMultipartFile requestPartJson = MockEntityFactory.mockMultipartFileJson("requestDto", requestDto);

        // when
        ResultActions actions = mvc.perform(multipart(HttpMethod.PUT, UPDATE_PROFILE_API)
                .file(requestPartImage)
                .file(requestPartJson)
                .header(Header.AUTH, accessToken)
                .with(csrf()));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(docs("user-profile-update",
                        requestHeaders(
                                header(Header.AUTH).description("액세스 토큰")
                        ),
                        requestParts(
                                part("requestDto").description("수정할 닉네임").optional(),
                                part("imageFile").description("수정할 프로필 이미지 파일").optional()
                        )));
    }
}
