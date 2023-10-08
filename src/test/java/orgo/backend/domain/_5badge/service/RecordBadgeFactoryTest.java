package orgo.backend.domain._5badge.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._5badge.entity.*;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.repository.AcquisitionRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.ServiceTest;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
class RecordBadgeFactoryTest {

    @InjectMocks
    RecordBadgeFactory recordBadgeFactory;

    @Mock
    BadgeRepository<Badge> badgeRepository;
    @Mock
    AcquisitionRepository acquisitionRepository;

    @Test
    @DisplayName("사용자가 RecordCountBadge를 획득한다.")
    void issueRecordCountBadge() {
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord climbingRecord = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 1, 15, 23, 44))
                .build();
        user.getClimbingRecords().add(climbingRecord);

        RecordCountBadge recordCountBadge = RecordCountBadge.builder()
                .id(1L)
                .mountain("아차산")
                .count(1)
                .build();

        List<Badge> badgesInRecordGroup = List.of(recordCountBadge);
        given(acquisitionRepository.save(any())).willReturn(new Acquisition(recordCountBadge, user));
        given(badgeRepository.findByMainGroup(BadgeGroup.RECORD)).willReturn(badgesInRecordGroup);

        //when
        recordBadgeFactory.issueAvailableBadges(user);

        //then
        Mockito.verify(badgeRepository).findByMainGroup(BadgeGroup.RECORD);
    }

    @Test
    @DisplayName("사용자가 RecordHeightBadge를 획득한다.")
    void issueRecordHeightBadge() {
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord climbingRecord = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 1, 15, 23, 44))
                .build();
        user.getClimbingRecords().add(climbingRecord);

        RecordHeightBadge recordHeightBadge = RecordHeightBadge.builder()
                .id(2L)
                .height(100.2)
                .build();

        List<Badge> badgesInRecordGroup = List.of(recordHeightBadge);
        given(acquisitionRepository.save(any())).willReturn(new Acquisition(recordHeightBadge, user));
        given(badgeRepository.findByMainGroup(BadgeGroup.RECORD)).willReturn(badgesInRecordGroup);

        //when
        recordBadgeFactory.issueAvailableBadges(user);

        //then
        Mockito.verify(badgeRepository).findByMainGroup(BadgeGroup.RECORD);
    }

    @Test
    @DisplayName("사용자가 RecordMonthBadge를 획득한다.")
    void issueRecordMonthBadge() {
        //given
        User user = MockEntityFactory.mockUser(1L);
        Mountain mountain = MockEntityFactory.mockMountain(1L, MockEntityFactory.mockPeak(1L));
        ClimbingRecord climbingRecord = ClimbingRecord.builder()
                .id(1L)
                .user(user)
                .mountain(mountain)
                .date(LocalDateTime.of(2023, 9, 1, 15, 23, 44))
                .build();
        user.getClimbingRecords().add(climbingRecord);

        RecordMonthBadge recordMonthBadge = RecordMonthBadge.builder()
                .id(3L)
                .yearMonth(YearMonth.of(2023, 9))
                .build();

        List<Badge> badgesInRecordGroup = List.of(recordMonthBadge);
        given(acquisitionRepository.save(any())).willReturn(new Acquisition(recordMonthBadge, user));
        given(badgeRepository.findByMainGroup(BadgeGroup.RECORD)).willReturn(badgesInRecordGroup);

        //when
        recordBadgeFactory.issueAvailableBadges(user);

        //then
        Mockito.verify(badgeRepository).findByMainGroup(BadgeGroup.RECORD);
    }


}