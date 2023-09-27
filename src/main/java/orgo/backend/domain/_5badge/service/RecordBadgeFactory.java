package orgo.backend.domain._5badge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.domain._5badge.repository.AcquisitionRepository;
import orgo.backend.domain._5badge.repository.BadgeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordBadgeFactory implements BadgeFactory {
    private final BadgeRepository<Badge> badgeRepository;
    private final AcquisitionRepository acquisitionRepository;

    @Override
    public List<Acquisition> issueAvailableBadges(User user) {
        List<Acquisition> newBadges = new ArrayList<>();
        List<Badge> badgesInRecordGroup = badgeRepository.findByMainGroup(BadgeGroup.RECORD);
        for (Badge badge : badgesInRecordGroup) {
            issueBadge(user, badge).ifPresent(newBadges::add);
        }
        return newBadges;
    }

    private Optional<Acquisition> issueBadge(User user, Badge badge) {
        if (badge.canIssue(user)){
            Acquisition acquisition = badge.issue(user);
            return Optional.of(acquisitionRepository.save(acquisition));
        }
        return Optional.empty();
    }
}
