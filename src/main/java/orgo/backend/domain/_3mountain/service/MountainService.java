package orgo.backend.domain._3mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.dto.MountainDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MountainService {
    private final MountainRepository mountainRepository;

    /**
     * 모든 산 목록을 반환합니다.
     * 검색어에 해당하는 산만 조회할 수 있습니다.
     *
     * @return 산 목록
     */
    public List<MountainDto.Response> findAll(String keywordNullable) {
        String keyword = Objects.requireNonNullElse(keywordNullable, "");
        return mountainRepository.findByKeyword(keyword).stream()
                .map(MountainDto.Response::new)
                .toList();
    }
}
