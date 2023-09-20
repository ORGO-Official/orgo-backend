package orgo.backend.domain._5badge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.time.YearMonth;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_month")
public class RecordMonthBadge extends Badge{

    private int year;
    private int month;

    @Builder
    public RecordMonthBadge(String condition, String description,  YearMonth yearMonth){
        super(BadgeGroup.RECORD, condition, description);
        this.year = yearMonth.getYear();
        this.month = yearMonth.getMonthValue();
    }

    public boolean check(Object object){
        return true;
    }
    
    @Override
    public Acquisition issue(User user) {
        return null;
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
