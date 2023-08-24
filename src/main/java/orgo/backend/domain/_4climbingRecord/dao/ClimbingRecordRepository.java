package orgo.backend.domain._4climbingRecord.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._4climbingRecord.domain.ClimbingRecord;

public interface ClimbingRecordRepository extends JpaRepository<ClimbingRecord, Long> {
    ClimbingRecord findByUserId(Long userId);
}
