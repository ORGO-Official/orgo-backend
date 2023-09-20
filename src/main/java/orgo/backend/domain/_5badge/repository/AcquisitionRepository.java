package orgo.backend.domain._5badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.util.List;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
    List<Acquisition> findByUser(User user);
}
