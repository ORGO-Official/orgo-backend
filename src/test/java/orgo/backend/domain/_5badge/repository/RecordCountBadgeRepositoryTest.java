package orgo.backend.domain._5badge.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;
import orgo.backend.domain._5badge.entity.RecordCountBadge;
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
        RecordCountBadge recordCountBadge = RecordCountBadge.builder()
                .mountain("아차산")
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
    @DisplayName("특정 산에 대해 1회, 3회, 5회 완등 조건을 가지는 RecordCountBadge를 저장한다.")
    void test(){
        //given
        Mountain mountain = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        RecordCountBadge countOneBadge = RecordCountBadge.builder().mountain("아차산").count(1).build();
        RecordCountBadge countThreeBadge = RecordCountBadge.builder().mountain("아차산").count(3).build();
        RecordCountBadge countFiveBadge = RecordCountBadge.builder().mountain("아차산").count(5).build();

        //when
        badgeRepository.save(countOneBadge);
        badgeRepository.save(countThreeBadge);
        badgeRepository.save(countFiveBadge);

        //then
        assertThat(badgeRepository.findAll()).hasSize(3);
    }


}
