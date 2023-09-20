package orgo.backend.domain._5badge.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;
import orgo.backend.domain._5badge.entity.RecordMonthBadge;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RecordMonthBadgeRepositoryTest extends RepositoryTest {

    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Autowired
    MountainRepository mountainRepository;

    @Test
    @DisplayName("RecordMonthBadge를 저장하고, 조회한다.")
    void saveAndFind() {
        //given
        RecordMonthBadge recordCountBadge = RecordMonthBadge.builder()
                .yearMonth(YearMonth.of(2023, 9))
                .build();

        //when
        RecordMonthBadge saved = badgeRepository.save(recordCountBadge);
        RecordMonthBadge found = (RecordMonthBadge) badgeRepository.findById(saved.getId()).get();
        log.info("{}", found);

        //then
        assertThat(saved).isEqualTo(found);
    }
}
