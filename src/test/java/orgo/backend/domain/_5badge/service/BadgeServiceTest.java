package orgo.backend.domain._5badge.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.RecordCountBadge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.repository.AcquisitionRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.ServiceTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ServiceTest
class BadgeServiceTest {
    @InjectMocks
    BadgeService badgeService;

    @Mock
    AcquisitionRepository acquisitionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BadgeRepository<Badge> badgeRepository;

    @Test
    @DisplayName("사용자가 획득한 뱃지를 조회한다.")
    void getAcquiredBadges(){
        //given
        long userId = 1L;
        User user = MockEntityFactory.mockUser(userId);
        Badge recordHeightBadge = MockEntityFactory.mockBadge(1L);
        Acquisition acquisition = new Acquisition(recordHeightBadge, user);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(acquisitionRepository.findByUser(user)).willReturn(List.of(acquisition));

        //when
        badgeService.getAcquiredBadges(userId);

        //then
        verify(userRepository).findById(userId);
        verify(acquisitionRepository).findByUser(user);
    }

    @Test
    @DisplayName("사용자가 획득하지 못한 뱃지를 조회한다.")
    void getNotAcquiredBadges(){
        //given
        long userId = 1L;
        User user = MockEntityFactory.mockUser(userId);
        Badge badge1 = MockEntityFactory.mockBadge(1L);
        Badge badge2 = MockEntityFactory.mockBadge(2L);

        Acquisition acquisition = new Acquisition(badge1, user);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(badgeRepository.findAll()).willReturn(List.of(badge1, badge2));
        given(acquisitionRepository.findByUser(user)).willReturn(List.of(acquisition));

        //when
        badgeService.getNotAcquiredBadges(userId);

        //then
        verify(userRepository).findById(userId);
        verify(badgeRepository).findAll();
        verify(acquisitionRepository).findByUser(user);
    }
}