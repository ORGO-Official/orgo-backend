package orgo.backend.domain._3mountain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.ServiceTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ServiceTest
class MountainServiceTest {
    @InjectMocks
    MountainService mountainService;

    @Mock
    MountainRepository mountainRepository;

    @Test
    @DisplayName("산 하나에 대한 정보를 반환한다. ")
    void test() {
        // given
        long mountainId = 1L;
        Mountain mountain = MockEntityFactory.mockMountain(mountainId, MockEntityFactory.mockPeak(1L));
        given(mountainRepository.findById(mountainId)).willReturn(Optional.of(mountain));

        // when
        mountainService.getMountain(mountainId);

        // then
        verify(mountainRepository).findById(mountainId);
    }
}