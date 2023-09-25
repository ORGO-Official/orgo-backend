package orgo.backend.domain._5badge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;

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

    @Override
    public boolean canIssue(User user) {
        boolean notHave = !user.hasBadge(this);
        boolean achieved = this.count <= user.countOfMountainClimbed(this.mountain);
        return notHave && achieved;
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
