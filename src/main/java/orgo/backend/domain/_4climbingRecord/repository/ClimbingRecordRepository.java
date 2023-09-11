package orgo.backend.domain._4climbingRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;

public interface ClimbingRecordRepository extends JpaRepository<ClimbingRecord, Long> {
    ClimbingRecord findByUserId(Long userId);
}
