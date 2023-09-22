package orgo.backend.domain._5badge.repository;

import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AcquisitionRepositoryTest extends RepositoryTest {
    @Autowired
    AcquisitionRepository acquisitionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRepository<Badge> badgeRepository;

    @Test
    @DisplayName("Acquisition을 저장하고 조회한다.")
    void saveAndFind(){
        //given
        User user = userRepository.save(MockEntityFactory.mockUser(null));
        Badge badge = badgeRepository.save(MockEntityFactory.mockBadge(null));

        //when
        Acquisition acquisition = acquisitionRepository.save(new Acquisition(badge, user));

        //then
        Acquisition found = acquisitionRepository.findById(acquisition.getId()).get();
        Assertions.assertThat(acquisition).isEqualTo(found);
    }


    @Test
    @DisplayName("미획득 뱃지를 조회한다. ")
    void findNotAcquired(){
        //given
        User user = userRepository.save(MockEntityFactory.mockUser(null));
        Badge badge1 = badgeRepository.save(MockEntityFactory.mockBadge(null));
        Badge badge2 = badgeRepository.save(MockEntityFactory.mockBadge(null));
        Badge badge3 = badgeRepository.save(MockEntityFactory.mockBadge(null));
        acquisitionRepository.save(new Acquisition(badge1, user));

        //when
        List<Badge> notAcquired = acquisitionRepository.findNotAcquired(user);

        //then
        Assertions.assertThat(notAcquired).containsExactlyInAnyOrder(badge2, badge3);
    }
}