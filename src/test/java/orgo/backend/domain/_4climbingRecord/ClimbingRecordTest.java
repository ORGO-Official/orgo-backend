package orgo.backend.domain._4climbingRecord;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.entity.Peak;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._4climbingRecord.dto.MyClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.Position;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.repository.ClimbingRecordRepository;
import orgo.backend.domain._4climbingRecord.service.ClimbingRecordService;
import orgo.backend.setting.MockEntityFactory;

import java.time.LocalDateTime;

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

    Peak peak = MockEntityFactory.mockPeak(null);
    Mountain mountain = MockEntityFactory.mockMountain(null, peak);
    User user = MockEntityFactory.mockUser(null);
    Mountain savedMountain;

    @BeforeAll
    void setSavedMountain() {
        savedMountain = mountainRepository.save(mountain);
    }

    @Test
    @DisplayName("두 위도 경도 간의 거리 계산")
    void calDistanceTest() {
        //given
        Position mountainPosition = Position.builder()
                .latitude(37.57149)
                .longitude(127.103764)
                .build();

        Position userPosition = Position.builder()
                .latitude(35.8714354)
                .longitude(128.601445)
                .build();

        //when
        double distanceFromMountainToUser = mountainPosition.calDistanceFrom(userPosition);
        double distanceFromMountainToMountain = mountainPosition.calDistanceFrom(mountainPosition);

        //then
        Assertions.assertEquals(0, distanceFromMountainToMountain);
        Assertions.assertNotEquals(0, distanceFromMountainToUser);
    }



    @Test
    @DisplayName("등반 완등 인증 가능한 위치인지 테스트")
    void isTopTest() {
        //given
        UserPosDto userPosAtTopDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        UserPosDto userPosNotAtTopDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude()+10)
                .latitude(mountain.getLocation().getLatitude()+10)
                .longitude(mountain.getLocation().getLongitude()+10)
                .build();

        //when
        boolean isTopResult = climbingRecordService.isTop(userPosAtTopDto);
        boolean isTopResult2 = climbingRecordService.isTop(userPosNotAtTopDto);

        //then
        Assertions.assertTrue(isTopResult);
        Assertions.assertFalse(isTopResult2);
    }

    @Test
    @DisplayName("등산 완등 인증")
    void registerClimbingRecordTest() {
        //given
        User savedUser = userRepository.save(user);

        UserPosDto userPosAtTopDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude())
                .latitude(mountain.getLocation().getLatitude())
                .longitude(mountain.getLocation().getLongitude())
                .build();

        UserPosDto userPosNotAtTopDto = UserPosDto.builder()
                .date(LocalDateTime.now())
                .mountainId(savedMountain.getId())
                .altitude(mountain.getLocation().getAltitude()+10)
                .latitude(mountain.getLocation().getLatitude()+10)
                .longitude(mountain.getLocation().getLongitude()+10)
                .build();

        //when
        climbingRecordService.registerClimbingRecord(savedUser.getId(), userPosAtTopDto);

        //then
        Long expectedUserId=1L;
        Long expectedMountainId=1L;

        ClimbingRecord savedClimbingRecord = climbingRecordRepository.findById(expectedUserId).orElseThrow();

        Assertions.assertEquals(expectedUserId, savedClimbingRecord.getUser().getId());
        Assertions.assertEquals(expectedMountainId, savedClimbingRecord.getMountain().getId());
        Assertions.assertThrows(RuntimeException.class, ()-> climbingRecordService.registerClimbingRecord(savedUser.getId(),
                userPosNotAtTopDto));
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
        climbingRecordService.registerClimbingRecord(savedUser.getId(), userPosDto);
        //List<ClimbingRecordDto> climbingRecordDtos = climbingRecordService.viewMyClimbingRecords(userId);
        MyClimbingRecordDto myClimbingRecordDto = climbingRecordService.viewMyClimbingRecords(userId);

        //then
        Long expectedClimbingCnt = 2L;
        double expectedClimbedAltitude = mountain.getLocation().getAltitude() * expectedClimbingCnt;

        Assertions.assertEquals(expectedClimbingCnt, myClimbingRecordDto.getClimbingCnt());
        Assertions.assertEquals(expectedClimbedAltitude, myClimbingRecordDto.getClimbedAltitude());
        Assertions.assertEquals(1L, myClimbingRecordDto.getClimbingRecordDtoList().get(0).getClimbingOrder());
        Assertions.assertEquals(2L, myClimbingRecordDto.getClimbingRecordDtoList().get(1).getClimbingOrder());
    }
}
