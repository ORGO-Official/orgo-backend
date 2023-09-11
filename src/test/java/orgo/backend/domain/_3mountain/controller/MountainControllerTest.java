package orgo.backend.domain._3mountain.controller;

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
import orgo.backend.domain._3mountain.dto.MountainDto;
import orgo.backend.domain._3mountain.dto.RestaurantDto;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.PlaceInfo;
import orgo.backend.domain._3mountain.service.MountainService;
import orgo.backend.domain._3mountain.service.RestaurantService;
import orgo.backend.global.config.security.JwtAuthenticationFilter;
import orgo.backend.global.config.security.SecurityConfig;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.WithCustomMockUser;

import java.util.List;
import java.util.stream.Stream;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsPathParam.pathParams;
import static hansol.restdocsdsl.docs.RestDocsQueryParam.queryParams;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.ParamElement.param;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(value = MountainController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
public class MountainControllerTest {

    private final static String GET_ALL_MOUNTAINS_API = "/api/mountains";
    private final static String GET_MOUNTAIN_API = "/api/mountains/{mountainId}";
    private final static String GET_RESTAURANT_API = "/api/mountains/{mountainId}/restaurants";

    @MockBean
    MountainService mountainService;

    @MockBean
    RestaurantService restaurantService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithCustomMockUser
    @DisplayName("[산 목록 조회] - 성공")
    void test() throws Exception {
        // given
        Mountain mountain1 = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        Mountain mountain2 = MockEntityFactory.mockMountain(2L, MockEntityFactory.mockPeak(2L));
        Mountain mountain3 = MockEntityFactory.mockMountain(3L, MockEntityFactory.mockPeak(3L));
        Mountain mountain4 = MockEntityFactory.mockMountain(4L, MockEntityFactory.mockPeak(4L));
        List<MountainDto.Response> responses = Stream.of(mountain1, mountain2, mountain3, mountain4)
                .map(MountainDto.Response::new)
                .toList();
        given(mountainService.getAllMountains(null)).willReturn(responses);

        // when
        ResultActions actions = mvc.perform(get(GET_ALL_MOUNTAINS_API)
                .with(csrf()));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("mountain-get-all",
                        queryParams(
                                param("keyword").description("검색어").optional(),
                                param("_csrf").description("").optional()
                        ),
                        responseFields(
                                field("[].id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("[].name").description("이름"),
                                field("[].description").description("소개"),
                                field("[].address").description("주소"),
                                field("[].contact").description("연락처"),
                                field("[].mainImage").description("메인 이미지"),
                                field("[].backgroundImage").description("배경 이미지"),
                                field("[].requiredTime").description("소요 시간"),
                                field("[].difficulty").description("난이도(EASY | NORMAL | HARD)"),
                                field("[].location").type(JsonFieldType.OBJECT).description("위치 정보"),
                                field("[].location.latitude").type(JsonFieldType.NUMBER).description("위도"),
                                field("[].location.longitude").type(JsonFieldType.NUMBER).description("경도"),
                                field("[].location.altitude").type(JsonFieldType.NUMBER).description("고도"),
                                field("[].featureTag").type(JsonFieldType.OBJECT).description("태그"),
                                field("[].featureTag.goodNightView").type(JsonFieldType.BOOLEAN).description("야경 맛집"),
                                field("[].featureTag.totalCourse").type(JsonFieldType.NUMBER).description("코스 수"),
                                field("[].featureTag.parkingLot").type(JsonFieldType.BOOLEAN).description("주차장"),
                                field("[].featureTag.restRoom").type(JsonFieldType.BOOLEAN).description("화장실"),
                                field("[].featureTag.cableCar").type(JsonFieldType.BOOLEAN).description("케이블카")
                        )));
    }


    @Test
    @WithCustomMockUser
    @DisplayName("[산 단건 조회] - 성공")
    void getMountain() throws Exception {
        // given
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        MountainDto.Response responseDto = new MountainDto.Response(mountain);
        given(mountainService.getMountain(1L)).willReturn(responseDto);

        // when
        ResultActions actions = mvc.perform(get(GET_MOUNTAIN_API, 1L));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("mountain-get",
                        pathParams(
                                param("mountainId").description("산 아이디넘버")
                        ),
                        responseFields(
                                field("id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("name").description("이름"),
                                field("description").description("소개"),
                                field("address").description("주소"),
                                field("contact").description("연락처"),
                                field("mainImage").description("메인 이미지"),
                                field("backgroundImage").description("배경 이미지"),
                                field("requiredTime").description("소요 시간"),
                                field("difficulty").description("난이도(EASY | NORMAL | HARD)"),
                                field("location").type(JsonFieldType.OBJECT).description("위치 정보"),
                                field("location.latitude").type(JsonFieldType.NUMBER).description("위도"),
                                field("location.longitude").type(JsonFieldType.NUMBER).description("경도"),
                                field("location.altitude").type(JsonFieldType.NUMBER).description("고도"),
                                field("featureTag").type(JsonFieldType.OBJECT).description("태그"),
                                field("featureTag.goodNightView").type(JsonFieldType.BOOLEAN).description("야경 맛집"),
                                field("featureTag.totalCourse").type(JsonFieldType.NUMBER).description("코스 수"),
                                field("featureTag.parkingLot").type(JsonFieldType.BOOLEAN).description("주차장"),
                                field("featureTag.restRoom").type(JsonFieldType.BOOLEAN).description("화장실"),
                                field("featureTag.cableCar").type(JsonFieldType.BOOLEAN).description("케이블카")
                        )));
    }


    @Test
    @WithCustomMockUser
    @DisplayName("[근처 식당 조회] - 성공")
    void test1() throws Exception {
        //given
        Long mountainId = 1L;
        RestaurantDto.Response response1 = new RestaurantDto.Response(new PlaceInfo("식당", "주소", 100, 1.1, 1.2, "01012345678", "image.jpg", "www.naver.com"));
        RestaurantDto.Response response2 = new RestaurantDto.Response(new PlaceInfo("식당", "주소", 100, 1.1, 1.2, "01012345678", "image.jpg", "www.naver.com"));
        RestaurantDto.Response response3 = new RestaurantDto.Response(new PlaceInfo("식당", "주소", 100, 1.1, 1.2, "01012345678", "image.jpg", "www.naver.com"));
        List<RestaurantDto.Response> response = List.of(response1, response2, response3);
        given(restaurantService.findNearbyRestaurant(mountainId)).willReturn(response);

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        actions.andExpect(status().isOk())
                .andDo(docs("mountain-achasan-restaurant",
                        pathParams(
                                param("mountainId").description("산 아이디넘버")
                        ),
                        responseFields(
                                field("[].name").description("이름"),
                                field("[].address").description("주소"),
                                field("[].distance").type(JsonFieldType.NUMBER).description("산으로부터 떨어진 거리(미터)"),
                                field("[].mapX").type(JsonFieldType.NUMBER).description("X좌표(longitude(경도))"),
                                field("[].mapY").type(JsonFieldType.NUMBER).description("Y좌표(latitude(위도))"),
                                field("[].contact").description("연락처").optional(),
                                field("[].imageUrl").description("사진 URL").optional(),
                                field("[].externalLink").description("외부 링크")
                        )
                ));
    }
}
