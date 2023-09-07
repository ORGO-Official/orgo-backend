package orgo.backend.domain._3mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._3mountain.repository.MountainRepository;
import orgo.backend.domain._3mountain.dto.MountainDto;
import orgo.backend.global.error.exception.MountainNotFoundException;

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
    public List<MountainDto.Response> getAllMountains(String keywordNullable) {
        String keyword = Objects.requireNonNullElse(keywordNullable, "");
        return mountainRepository.findByKeyword(keyword).stream()
                .map(MountainDto.Response::new)
                .toList();
    }

    public MountainDto.Response getMountain(long mountainId) {
        Mountain mountain = mountainRepository.findById(mountainId).orElseThrow(MountainNotFoundException::new);
        return new MountainDto.Response(mountain);
    }
}
