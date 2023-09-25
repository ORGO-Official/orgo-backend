package orgo.backend.domain._5badge.service;

import orgo.backend.domain._2user.entity.User;

public interface BadgeFactory {
    void issueAvailableBadges(User user);
}
