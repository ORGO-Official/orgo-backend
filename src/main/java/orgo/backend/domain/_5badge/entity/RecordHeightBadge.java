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

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_height")
public class RecordHeightBadge extends Badge{
    @OneToOne
    @JoinColumn
    private Mountain mountain;
    private double height;

    @Builder
    public RecordHeightBadge(Mountain mountain, double height, BadgeGroup mainGroup){
        super(mainGroup);
        this.mountain = mountain;
        this.height = height;
    }

    public boolean check(List<Record> records){
        return true;
    }
    
    @Override
    public void issue(User user) {
        
    }

    @Override
    public String toString() {
        return "RecordCountBadge{" +
                "mountain=" + mountain.getName() +
                ", count=" + height +
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
