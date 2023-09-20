package orgo.backend.domain._5badge.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.RepositoryTest;

import java.util.List;

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



}
