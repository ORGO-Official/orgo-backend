package orgo.backend.domain._5badge.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;
import orgo.backend.domain._5badge.entity.RecordHeightBadge;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RecordHeightBadgeRepositoryTest extends RepositoryTest {

    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Autowired
    MountainRepository mountainRepository;

    @Test
    @DisplayName("RecordHeightBadge를 저장하고, 조회한다.")
    void saveAndFind() {
        //given
        Mountain mountain = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordHeightBadge recordCountBadge = RecordHeightBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain)
                .height(100.5)
                .build();

        //when
        RecordHeightBadge saved = badgeRepository.save(recordCountBadge);
        RecordHeightBadge found = (RecordHeightBadge) badgeRepository.findById(saved.getId()).get();
        log.info("{}", found);

        //then
        assertThat(saved).isEqualTo(found);
    }
}
