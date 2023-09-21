package orgo.backend.domain._5badge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.repository.AcquisitionRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;
import orgo.backend.global.error.exception.UserNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {
    private final UserRepository userRepository;
    private final BadgeRepository<Badge> badgeRepository;
    private final AcquisitionRepository acquisitionRepository;

    public List<BadgeDto.Acquired> getAcquiredBadges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Acquisition> acquisitions = acquisitionRepository.findByUser(user);
        return acquisitions.stream()
                .map(BadgeDto.Acquired::new)
                .toList();
    }

    public List<BadgeDto.NotAcquired> getNotAcquiredBadges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<Badge> wholeBadges = findWholeBadges();
        Set<Badge> acquiredBadges = findAcquiredBadges(user);

        wholeBadges.removeAll(acquiredBadges);
        return wholeBadges.stream()
                .map(BadgeDto.NotAcquired::new)
                .toList();
    }

    private Set<Badge> findWholeBadges() {
        List<Badge> wholeBadges = badgeRepository.findAll();
        return new HashSet<>(wholeBadges);
    }

    private Set<Badge> findAcquiredBadges(User user) {
        List<Badge> acquiredBadges = acquisitionRepository.findByUser(user)
                .stream()
                .map(Acquisition::getBadge)
                .toList();
        return new HashSet<>(acquiredBadges);
    }
}
