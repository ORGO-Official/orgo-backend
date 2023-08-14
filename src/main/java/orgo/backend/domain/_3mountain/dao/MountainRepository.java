package orgo.backend.domain._3mountain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._3mountain.domain.Mountain;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
}
