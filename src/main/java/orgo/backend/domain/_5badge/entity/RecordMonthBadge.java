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

import java.time.YearMonth;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_month")
public class RecordMonthBadge extends Badge{
    @OneToOne
    @JoinColumn
    private Mountain mountain;
    private int year;
    private int month;

    @Builder
    public RecordMonthBadge(Mountain mountain, YearMonth yearMonth, BadgeGroup mainGroup){
        super(mainGroup);
        this.mountain = mountain;
        this.year = yearMonth.getYear();
        this.month = yearMonth.getMonthValue();
    }

    public boolean check(List<Record> records){
        return true;
    }
    
    @Override
    public void issue(User user) {
        
    }

    public YearMonth getYearMonth(){
        return YearMonth.of(this.year, this.month);
    }

    @Override
    public String toString() {
        return "RecordCountBadge{" +
                "mountain=" + mountain.getName() +
                ", yearMonth=" + year + month+
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
