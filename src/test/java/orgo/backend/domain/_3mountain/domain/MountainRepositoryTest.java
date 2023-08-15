package orgo.backend.domain._3mountain.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.setting.MockEntityFactory;
import orgo.backend.setting.RepositoryTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MountainRepositoryTest extends RepositoryTest {
    @Autowired
    MountainRepository mountainRepository;

    @Test
    @DisplayName("Mountain 엔티티가 저장될 때, Peak 엔티티도 함께 저장된다. ")
    void test() {
        // given
        Peak peak = MockEntityFactory.mockPeak();
        Mountain mountain = MockEntityFactory.mockMountain(peak);

        // when
        Mountain saved = mountainRepository.save(mountain);

        // then
        Optional<Mountain> found = mountainRepository.findById(saved.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getPeaks()).isNotNull();
    }
}