package orgo.backend.domain._5badge.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("record_count")
public class RecordCountBadge extends Badge{
    @OneToOne
    @JoinColumn
    private Mountain mountain;
    private int count;

    @Builder
    public RecordCountBadge(Mountain mountain, int count, BadgeGroup mainGroup){
        super(mainGroup);
        this.mountain = mountain;
        this.count = count;
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
                ", count=" + count +
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
