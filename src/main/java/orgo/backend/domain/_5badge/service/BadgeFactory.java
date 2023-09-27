package orgo.backend.domain._5badge.service;

import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.util.List;

public interface BadgeFactory {
    List<Acquisition> issueAvailableBadges(User user);
}
