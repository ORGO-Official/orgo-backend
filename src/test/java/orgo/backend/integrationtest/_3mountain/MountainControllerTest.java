package orgo.backend.integrationtest._3mountain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.domain._3mountain.domain.Peak;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.MockEntityFactory;

import java.nio.charset.StandardCharsets;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsPathParam.pathParams;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static hansol.restdocsdsl.element.ParamElement.param;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class MountainControllerTest extends IntegrationTest {

    private final static String GET_ALL_API = "/api/mountains";
    private final static String GET_RESTAURANT_API = "/api/mountains/{mountainId}/restaurants";

    @Autowired
    MountainRepository mountainRepository;


    @Test
    @DisplayName("[산 목록 조회] - 성공 (더미데이터 기반 테스트)")
    void test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(get(GET_ALL_API));

        // then
        String responseBody = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].name", equalTo("아차산")))
                .andExpect(jsonPath("$[1].name", equalTo("인왕산")))
                .andExpect(jsonPath("$[2].name", equalTo("청계산")))
                .andExpect(jsonPath("$[3].name", equalTo("북한산")))
                .andExpect(jsonPath("$[4].name", equalTo("관악산")))
                .andExpect(jsonPath("$[5].name", equalTo("용마산")))
                .andExpect(jsonPath("$[6].name", equalTo("수락산")))
                .andExpect(jsonPath("$[7].name", equalTo("안산")))
                .andExpect(jsonPath("$[8].name", equalTo("도봉산")))
                .andExpect(jsonPath("$[9].name", equalTo("불암산")))
                .andDo(docs("mountain-get-all",
                        responseFields(
                                field("[].id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("[].name").description("이름"),
                                field("[].description").description("소개"),
                                field("[].address").description("주소"),
                                field("[].contact").description("연락처"),
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
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseBody);
    }

    @Test
    @DisplayName("[근처 식당 조회(아차산)] - 성공")
    void test1() throws Exception {
        //given
        Long mountainId = 1L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(인왕산)] - 성공")
    void test2() throws Exception {
        //given
        Long mountainId = 2L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-inwangsan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(청계산)] - 성공")
    void test3() throws Exception {
        //given
        Long mountainId = 3L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-chungyesan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(북한산)] - 성공")
    void test4() throws Exception {
        //given
        Long mountainId = 4L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-bukhansan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(관악산)] - 성공")
    void test5() throws Exception {
        //given
        Long mountainId = 5L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-gwanaksan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(용마산)] - 성공")
    void test6() throws Exception {
        //given
        Long mountainId = 6L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-yongmasan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(수락산)] - 성공")
    void test7() throws Exception {
        //given
        Long mountainId = 7L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-suraksan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(안산)] - 성공")
    void test8() throws Exception {
        //given
        Long mountainId = 8L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-ansan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(도봉산)] - 성공")
    void test9() throws Exception {
        //given
        Long mountainId = 9L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-dobongsan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }

    @Test
    @DisplayName("[근처 식당 조회(불암산)] - 성공")
    void test10() throws Exception {
        //given
        Long mountainId = 10L;

        //when
        ResultActions actions = mvc.perform(get(GET_RESTAURANT_API, mountainId));

        //then
        String response = actions.andExpect(status().isOk())
                .andDo(docs("mountain-bullamsan-restaurant",
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
                                field("[].externalLink").description("외부 링크").optional()
                        )
                ))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(response);
    }
}
