package orgo.backend.domain._5badge.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.entity.*;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BadgeRepositoryTest extends RepositoryTest {
    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Autowired
    MountainRepository mountainRepository;


    @Test
    @DisplayName("모든 뱃지를 조회한다.")
    void getAllBadges(){
        //given

        //when
        badgeRepository.findAll();

        //then

    }

    @Test
    @DisplayName("RECORD 그룹에 속한 뱃지 목록을 조회한다.")
    void getAllRecordBadges(){
        //given
        Mountain mountain = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordCountBadge recordCountBadge = RecordCountBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain)
                .count(1)
                .build();
        badgeRepository.save(recordCountBadge);

        RecordHeightBadge recordHeightBadge = RecordHeightBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain)
                .height(100.2)
                .build();
        badgeRepository.save(recordHeightBadge);

        RecordMonthBadge recordMonthBadge = RecordMonthBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain)
                .yearMonth(YearMonth.of(2023, 9))
                .build();
        badgeRepository.save(recordMonthBadge);

        //when
        List<Badge> badges = badgeRepository.findByMainGroup(BadgeGroup.RECORD);

        //then
        assertThat(badges).hasSize(3);
    }



}
