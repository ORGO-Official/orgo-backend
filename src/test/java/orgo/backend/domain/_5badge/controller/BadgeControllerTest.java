package orgo.backend.domain._5badge.controller;

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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._3mountain.controller.MountainController;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.service.BadgeService;
import orgo.backend.global.config.security.JwtAuthenticationFilter;
import orgo.backend.global.config.security.SecurityConfig;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.WithCustomMockUser;

import java.time.LocalDateTime;
import java.util.List;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsHeader.requestHeaders;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.HeaderElement.header;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(value = BadgeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
class BadgeControllerTest {

    private final static String GET_ACQUIRED_BADGES = "/api/badges/acquired";

    @MockBean
    BadgeService badgeService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser(userId = "1")
    @DisplayName("[보유 뱃지 조회]")
    void getAcquiredBadges() throws Exception {
        //given
        String accessToken = "accessToken";
        BadgeDto.Acquired responseDto = new BadgeDto.Acquired(1L, "아차산 등반 1회", "아차산 등반 1회 달성 시 주어지는 뱃지입니다.", LocalDateTime.now());
        given(badgeService.getAcquiredBadges(1L)).willReturn(List.of(responseDto));

        //when
        ResultActions actions = mvc.perform(get(GET_ACQUIRED_BADGES)
                .header(Header.AUTH, accessToken)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(docs("badge-acquired",
                        requestHeaders(
                                header(Header.AUTH).description("액세스 토큰")
                        ),
                        responseFields(
                                field("[].id").type(JsonFieldType.NUMBER).description("뱃지 아이디 넘버"),
                                field("[].objective").description("뱃지 획득을 위한 목표"),
                                field("[].description").description("뱃지 설명"),
                                field("[].acquiredTime").description("뱃지 획득 시간")
                        )));
    }


}