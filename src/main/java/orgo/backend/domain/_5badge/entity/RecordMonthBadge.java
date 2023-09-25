package orgo.backend.domain._5badge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;

import java.time.YearMonth;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_month")
public class RecordMonthBadge extends Badge{

    private int year;
    private int month;

    @Builder
    public RecordMonthBadge(Long id, String condition, String description,  YearMonth yearMonth){
        super(BadgeGroup.RECORD, condition, description);
        this.id = id;
        this.year = yearMonth.getYear();
        this.month = yearMonth.getMonthValue();
    }

    public boolean canIssue(User user){
        boolean notHave = !user.hasBadge(this);
        return notHave && user.hasClimbedAt(getYearMonth());
    }


    public YearMonth getYearMonth(){
        return YearMonth.of(this.year, this.month);
    }

    @Override
    public String toString() {
        return "RecordCountBadge{" +
                ", yearMonth=" + year + month+
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
