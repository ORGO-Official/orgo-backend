package orgo.backend.domain._3mountain.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._3mountain.dao.MountainRepository;
import orgo.backend.domain._3mountain.dto.MountainDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {
    private final MountainRepository mountainRepository;

    /**
     * 모든 산 목록을 반환합니다.
     *
     * @return 산 목록
     */
    public List<MountainDto.Response> findAll() {
        return mountainRepository.findAll().stream()
                .map(MountainDto.Response::new)
                .toList();
    }
}
