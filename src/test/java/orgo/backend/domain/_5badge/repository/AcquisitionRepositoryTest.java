package orgo.backend.domain._5badge.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;
import orgo.backend.domain._5badge.entity.RecordCountBadge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

class AcquisitionRepositoryTest extends RepositoryTest {
    @Autowired
    AcquisitionRepository acquisitionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Autowired
    MountainRepository mountainRepository;


    @Test
    @DisplayName("Acquisition을 저장하고 조회한다.")
    void saveAndFind(){
        //given
        User user = userRepository.save(MockEntityFactory.mockUser(null));
        Mountain mountain = mountainRepository.save(MockEntityFactory.mockMountain(null, MockEntityFactory.mockPeak(null)));
        Badge badge = badgeRepository.save(RecordCountBadge.builder().mainGroup(BadgeGroup.RECORD).mountain(mountain).count(1).build());

        //when
        Acquisition acquisition = acquisitionRepository.save(new Acquisition(badge, user));

        //then
        Acquisition found = acquisitionRepository.findById(acquisition.getId()).get();
        Assertions.assertThat(acquisition).isEqualTo(found);
    }
}