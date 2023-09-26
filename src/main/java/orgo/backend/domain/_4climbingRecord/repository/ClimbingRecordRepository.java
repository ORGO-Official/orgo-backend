package orgo.backend.domain._4climbingRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;

import java.util.List;

public interface ClimbingRecordRepository extends JpaRepository<ClimbingRecord, Long> {
    ClimbingRecord findByUserId(Long userId);

    List<ClimbingRecord> findByUserAndMountain(User user, Mountain mountain);
}
