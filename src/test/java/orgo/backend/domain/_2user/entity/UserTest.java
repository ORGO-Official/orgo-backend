package orgo.backend.domain._2user.entity;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.setting.MockEntityFactory;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Slf4j
class UserTest {

    @Test
    @DisplayName("사용자가 해당 뱃지를 보유하고 있을 경우, True를 반환한다.")
    void hasBadge(){
        //given
        User user = MockEntityFactory.mockUser(1L);
        Badge badge = MockEntityFactory.mockBadge(null);
        Acquisition acquisition = new Acquisition(badge, user);
        user.getAcquisitions().add(acquisition);

        //when
        boolean result = user.hasBadge(badge);

        //then
        Assertions.assertThat(result).isTrue();
    }


    @Test
    @DisplayName("사용자가 특정 산을 등반한 횟수를 조회한다.")
    void getCountOfMountainClimbed(){
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord record1 = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 1, 15, 23, 44))
                .build();

        ClimbingRecord record2 = ClimbingRecord.builder()
                .id(2L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 2, 15, 23, 44))
                .build();

        //when
        long result = user.countOfMountainClimbed(mountain.getName());

        //then
        Assertions.assertThat(result).isEqualTo(2L);
    }

    @Test
    @DisplayName("사용자가 총 오른 높이를 계산한다.")
    void sumHeightOfRecord(){
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord record1 = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 1, 15, 23, 44))
                .build();

        ClimbingRecord record2 = ClimbingRecord.builder()
                .id(2L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 2, 15, 23, 44))
                .build();

        //when
        double result = user.sumHeightOfRecords();

        //then
        Assertions.assertThat(result).isEqualTo(mountain.getLocation().getAltitude() * 2);
    }

    @Test
    @DisplayName("사용자가 해당 월에 등반했는지 확인한다.")
    void hasClimbedAt(){
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord record1 = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 8, 1, 15, 23, 44))
                .build();

        ClimbingRecord record2 = ClimbingRecord.builder()
                .id(2L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 2, 15, 23, 44))
                .build();

        //when
        boolean result = user.hasClimbedAt(YearMonth.of(2023, 9));

        //then
        Assertions.assertThat(result).isTrue();
    }
}