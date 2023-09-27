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

@Service
@RequiredArgsConstructor
public class RecordBadgeFactory implements BadgeFactory {
    private final BadgeRepository<Badge> badgeRepository;
    private final AcquisitionRepository acquisitionRepository;
    public List<Acquisition> issueAvailableBadges(User user) {
        List<Acquisition> newBadges = new ArrayList<>();
        List<Badge> badgesInRecordGroup = badgeRepository.findByMainGroup(BadgeGroup.RECORD);
        for (Badge badge : badgesInRecordGroup) {
            newBadges.add(issueIfAvailable(user, badge));
        }
        return newBadges;
    }

    private Acquisition issueIfAvailable(User user, Badge badge) {
        if (badge.canIssue(user)){
            Acquisition acquisition = badge.issue(user);
            return acquisitionRepository.save(acquisition);
        }
    }
}
