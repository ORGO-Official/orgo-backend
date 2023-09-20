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
    public RecordHeightBadge(Mountain mountain, double height){
        super(BadgeGroup.RECORD);
        this.mountain = mountain;
        this.height = height;
    }

    public boolean check(Object object){
        return true;
    }
    
    @Override
    public Acquisition issue(User user) {
        return null;
    }

    @Override
    public String toString() {
        return "RecordCountBadge{" +
                "mountain=" + mountain.getName() +
                ", height=" + height +
                ", id=" + id +
                ", group=" + mainGroup +
                '}';
    }
}
