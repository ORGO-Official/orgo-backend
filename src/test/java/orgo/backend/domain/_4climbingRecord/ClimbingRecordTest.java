package orgo.backend.domain._4climbingRecord;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
import orgo.backend.setting.MockEntityFactory;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@ActiveProfiles("test")
public class ClimbingRecordTest {

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
    @DisplayName("두 위도 경도 간의 거리 계산 - [0]")
    void calDistanceTest() {
        //given
        double mountainLatitude = mountain.getLocation().getLatitude();
        double mountainLongitude = mountain.getLocation().getLongitude();

        //when
        double actualDistance = climbingRecordService.calDistance(mountainLatitude,mountainLongitude,mountainLatitude,mountainLongitude);

        //then
        Assertions.assertEquals(0, actualDistance);
    }

    @Test
    @DisplayName("등반 완등 인증 가능한 위치인지 테스트 - [성공]")
    void isTopTest() {
        //given
        UserPosDto userPosDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        //when
        boolean actualIsTopResult = climbingRecordService.isTop(userPosDto);

        //then
        Assertions.assertTrue(actualIsTopResult);
    }

    @Test
    @DisplayName("등산 완등 인증 - [성공]")
    void registerClimbingRecordTest() {
        //given
        User savedUser = userRepository.save(user);

        UserPosDto userPosDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        //when
        climbingRecordService.registerClimbingRecord(savedUser.getId(), userPosDto);

        //then
        Long expectedUserId=1L;
        Long expectedMountainId=1L;

        ClimbingRecord savedClimbingRecord = climbingRecordRepository.findById(expectedUserId).orElseThrow();

        Assertions.assertEquals(expectedUserId, savedClimbingRecord.getUser().getId());
        Assertions.assertEquals(expectedMountainId, savedClimbingRecord.getMountain().getId());
    }

    @Test
    @DisplayName("사용자의 모든 완등기록을 조회합니다")
    void viewMyClimbingRecordsTest() {
        //given
        User savedUser = userRepository.save(user);

        UserPosDto userPosDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        //when
        Long userId=1L;

        climbingRecordService.registerClimbingRecord(savedUser.getId(), userPosDto);
        List<ClimbingRecordDto> climbingRecordDtos = climbingRecordService.viewMyClimbingRecords(userId);

        //then
        Long expectedClimbingRecordCnt = 1L;
        Long expectedMountainId=1L;

        Assertions.assertEquals(expectedClimbingRecordCnt, climbingRecordDtos.size());
        Assertions.assertEquals(expectedMountainId, climbingRecordDtos.get(0).getMountainId());
        Assertions.assertEquals(mountain.getName(), climbingRecordDtos.get(0).getMountainName());
    }
}
