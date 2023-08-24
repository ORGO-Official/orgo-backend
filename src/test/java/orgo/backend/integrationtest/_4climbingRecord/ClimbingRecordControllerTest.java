package orgo.backend.integrationtest._4climbingRecord;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.domain._3mountain.domain.Peak;
import orgo.backend.domain._4climbingRecord.application.ClimbingRecordService;
import orgo.backend.domain._4climbingRecord.dao.ClimbingRecordRepository;
import orgo.backend.domain._4climbingRecord.domain.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.global.constant.Header;
import orgo.backend.setting.IntegrationTest;
import orgo.backend.setting.MockEntityFactory;

import java.time.LocalDateTime;
import java.util.List;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsResponse.responseFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClimbingRecordControllerTest extends IntegrationTest {

    private final static String VIEW_CLIMBINGRECORDS_API = "/api/{userId}/climbingrecords";
    private final static String REGISTER_CLIMBINGRECORDS_API = "/api/climbingrecords";

    @Autowired
    MountainRepository mountainRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClimbingRecordRepository climbingRecordRepository;
    @Autowired
    ClimbingRecordService climbingRecordService;

    Peak peak = MockEntityFactory.mockPeak();
    Mountain mountain = MockEntityFactory.mockMountain(peak);
    User user = MockEntityFactory.mockUser();
    Mountain savedMountain;

    @BeforeAll
    void setSavedMountain() {
        savedMountain = mountainRepository.save(mountain);
    }

    @Test
    @DisplayName("사용자의 모든 완등기록을 조회합니다")
    void viewMyClimbingRecordsTest() throws Exception{
        //given
        User savedUser = userRepository.save(user);

        UserPosDto userPosDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        climbingRecordService.registerClimbingRecord(savedUser.getId(), userPosDto);

        ResultActions actions = mvc.perform(get(VIEW_CLIMBINGRECORDS_API,1L));

        //then
        actions.andExpect(status().isOk())
                .andDo(docs("climbingRecords-get-all",
                        responseFields(
                                field("[].id").type(JsonFieldType.NUMBER).description("아이디넘버"),
                                field("[].mountainId").type(JsonFieldType.NUMBER).description("산 아이디넘버"),
                                field("[].mountainName").description("산 이름"),
                                field("[].date").description("완등 날짜")
                        )
                ));
    }

}