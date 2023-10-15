package orgo.backend.domain._4climbingRecord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.Peak;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.MyClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.service.ClimbingRecordService;
import orgo.backend.global.config.security.JwtAuthenticationFilter;
import orgo.backend.global.config.security.SecurityConfig;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.WithCustomMockUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsRequest.requestFields;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@WebMvcTest(value = ClimbingRecordController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
public class ClimbingRecordControllerTest {

    private final static String VIEW_CLIMBINGRECORDS_API = "/api/climbing-records";
    private final static String REGISTER_CLIMBINGRECORDS_API = "/api/climbing-records";

    @MockBean
    ClimbingRecordService climbingRecordService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    Peak peak = MockEntityFactory.mockPeak(1L);
    Mountain mountain = MockEntityFactory.mockMountain(1L, peak);
    User user = MockEntityFactory.mockUser(1L);

    @Test
    @WithCustomMockUser
    @DisplayName("등산 완등 인증 요청을 처리합니다")
    void registerClimbingRecordsTest() throws Exception {
        //given
        String accessToken = "access-token";
        UserPosDto userPosDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(mountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        //when
        ResultActions actions = mvc.perform(post(REGISTER_CLIMBINGRECORDS_API)
                .header(Header.AUTH, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPosDto))
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(docs("climbingRecords-post",
                        requestFields(
                                field("mountainId").type(JsonFieldType.NUMBER).description("산 아이디넘버"),
                                field("latitude").type(JsonFieldType.NUMBER).description("산 위도"),
                                field("longitude").type(JsonFieldType.NUMBER).description("산 경도"),
                                field("altitude").type(JsonFieldType.NUMBER).description("산 고도"),
                                field("date").description("완등 날짜")
                        )
                ));
    }

    @Test
    @WithCustomMockUser
    @DisplayName("사용자의 모든 완등기록을 조회합니다")
    void viewMyClimbingRecordsTest() throws Exception {
        //given
        ClimbingRecord climbingRecord1 = new ClimbingRecord(1L, LocalDateTime.now(), user, mountain);
        ClimbingRecord climbingRecord2 = new ClimbingRecord(2L, LocalDateTime.now(), user, mountain);
        ClimbingRecord climbingRecord3 = new ClimbingRecord(3L, LocalDateTime.now(), user, mountain);
        List<ClimbingRecordDto> climbingRecordDtoList = Stream.of(climbingRecord1, climbingRecord2, climbingRecord3)
                .map(ClimbingRecordDto::new)
                .toList();
        MyClimbingRecordDto response = MyClimbingRecordDto.builder()
                .climbingRecordDtoList(climbingRecordDtoList)
                .build();
        response.setClimbedAltitudeByMyClimbingRecordList();
        response.setClimbingCntByMyClimbingRecordList();

        given(climbingRecordService.viewMyClimbingRecords(user.getId())).willReturn(response);
        ResultActions actions = mvc.perform(get(VIEW_CLIMBINGRECORDS_API)
                .with(csrf()));


        //then
        actions.andExpect(status().isOk())
                .andDo(docs("climbingRecords-get-all",
                        responseFields(
                                field("climbedAltitude").type(JsonFieldType.NUMBER).description("총 고도"),
                                field("climbingCnt").type(JsonFieldType.NUMBER).description("총 완등 횟수"),
                                field("climbingRecordDtoList[].id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("climbingRecordDtoList[].mountainId").type(JsonFieldType.NUMBER).description("산 아이디넘버"),
                                field("climbingRecordDtoList[].mountainName").description("산 이름"),
                                field("climbingRecordDtoList[].date").description("완등 날짜"),
                                field("climbingRecordDtoList[].altitude").type(JsonFieldType.NUMBER).description("산 고도"),
                                field("climbingRecordDtoList[].climbingOrder").type(JsonFieldType.NUMBER).description("등반 회차")
                        )
                ));
    }

}