package orgo.backend.integrationtest._3mountain;

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

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MountainControllerTest extends IntegrationTest {

    private final static String GET_ALL_API = "/api/mountains";

    @Autowired
    MountainRepository mountainRepository;


    @Test
    @DisplayName("[산 목록 조회] - 성공")
    void test() throws Exception {
        // given
        Peak peak = MockEntityFactory.mockPeak();
        Mountain mountain = MockEntityFactory.mockMountain(peak);
        mountainRepository.save(mountain);

        // when
        ResultActions actions = mvc.perform(get(GET_ALL_API));

        // then
        actions.andExpect(status().isOk())
                .andDo(docs("mountain-get-all",
                        responseFields(
                                field("[].id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("[].name").description("이름"),
                                field("[].description").description("소개"),
                                field("[].address").description("주소"),
                                field("[].contact").description("연락처"),
                                field("[].difficulty").description("난이도"),
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
                ));
    }
}
