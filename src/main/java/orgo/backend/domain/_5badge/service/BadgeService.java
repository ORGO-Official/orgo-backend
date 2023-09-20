package orgo.backend.domain._5badge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.repository.AcquisitionRepository;
import orgo.backend.global.error.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final UserRepository userRepository;
    private final AcquisitionRepository acquisitionRepository;
    public List<BadgeDto.Acquired> getAcquiredBadges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Acquisition> acquisitions = acquisitionRepository.findByUser(user);
        return acquisitions.stream()
                .map(BadgeDto.Acquired::new)
                .toList();
    }
}
