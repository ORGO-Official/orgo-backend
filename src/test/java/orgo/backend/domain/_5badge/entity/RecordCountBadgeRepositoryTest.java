package orgo.backend.domain._5badge.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RecordCountBadgeRepositoryTest extends RepositoryTest {

    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Autowired
    MountainRepository mountainRepository;

    @Test
    @DisplayName("RecordCountBadge를 저장하고, 조회한다.")
    void saveAndFind() {
        //given
        Mountain mountain = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordCountBadge recordCountBadge = RecordCountBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain)
                .count(1)
                .build();

        //when
        RecordCountBadge saved = badgeRepository.save(recordCountBadge);
        RecordCountBadge found = (RecordCountBadge) badgeRepository.findById(saved.getId()).get();
        log.info("{}", found);

        //then
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("RECORD 그룹에 속한 뱃지 목록을 조회한다.")
    void getAllRecordBadges(){
        //given
        Mountain mountain1 = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordCountBadge recordCountBadge1 = RecordCountBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain1)
                .count(1)
                .build();
        badgeRepository.save(recordCountBadge1);

        Mountain mountain2 = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordCountBadge recordCountBadge2 = RecordCountBadge.builder()
                .mainGroup(BadgeGroup.RECORD)
                .mountain(mountain2)
                .count(3)
                .build();
        badgeRepository.save(recordCountBadge2);

        //when
        List<Badge> badges = badgeRepository.findByMainGroup(BadgeGroup.RECORD);

        //then
        assertThat(badges).hasSize(2);
    }
}
