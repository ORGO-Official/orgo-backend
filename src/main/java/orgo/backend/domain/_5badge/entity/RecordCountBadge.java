package orgo.backend.domain._5badge.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_count")
public class RecordCountBadge extends Badge {
    private String mountain;
    private int count;

    @Builder
    public RecordCountBadge(Long id, String condition, String description, String mountain, int count) {
        super(BadgeGroup.RECORD, condition, description);
        this.id = id;
        this.mountain = mountain;
        this.count = count;
    }

    /**
     * 뱃지를 발급 가능한지 확인합니다.
     *
     * @param object 사용자의 전체 완등 기록
     * @return 뱃지 발급 가능 여부
     */
    @Override
    public boolean canIssue(Object object) {
        return true;
    }

    @Override
    public Acquisition issue(User user) {
        return null;
    }

    @Override
    public String toString() {
        return "RecordCountBadge{" +
                "mountain=" + mountain +
                ", count=" + count +
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
