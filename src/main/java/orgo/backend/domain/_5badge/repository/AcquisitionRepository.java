package orgo.backend.domain._5badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
}
