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

    @Test
    @DisplayName("이름에 특정 문자열이 포함된 산을 조회한다.")
    void test1() {
        // given
        Peak peak = MockEntityFactory.mockPeak();
        Mountain mountain = MockEntityFactory.mockMountain(peak);
        mountainRepository.save(mountain);

        String keyword = mountain.getName().substring(1);

        // when
        List<String> mountainNames = mountainRepository.findByKeyword(keyword).stream()
                .map(Mountain::getName)
                .toList();

        // then
        assertThat(mountainNames).contains(mountain.getName());
    }
}