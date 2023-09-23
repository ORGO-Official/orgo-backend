package orgo.backend.domain._5badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.BadgeGroup;

import java.util.List;
import java.util.Set;

public interface BadgeRepository<T extends Badge> extends JpaRepository<T, Long> {

    List<Badge> findByMainGroup(BadgeGroup mainGroup);
}
