package orgo.backend.domain._5badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.util.List;
import java.util.Set;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
    List<Acquisition> findByUser(User user);
    @Query(" select b from Badge b " +
            "where b not in (select ub from Badge ub " +
            "inner join Acquisition a on a.badge = ub " +
            "inner join User u on a.user = :user) ")
    List<Badge> findNotAcquired(User user);
}
