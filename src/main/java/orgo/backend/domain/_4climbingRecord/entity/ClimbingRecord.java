package orgo.backend.domain._4climbingRecord.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.global.config.jpa.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class ClimbingRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private LocalDateTime date;
    @ManyToOne
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mountain_id")
    private Mountain mountain;

    @Builder
    public ClimbingRecord(Long id, LocalDateTime date, User user, Mountain mountain) {
        this.id = id;
        this.date = date;
        changeUser(user);
        changeMountain(mountain);
    }

        public void changeUser(User user) {
        if(this.user != null) {
            this.user.getClimbingRecords().remove(this);
        }
        this.user = user;
        user.getClimbingRecords().add(this);
    }

    public void changeMountain(Mountain mountain) {
        if(this.mountain != null) {
            this.mountain.getClimbingRecords().remove(this);
        }
        this.mountain = mountain;
        mountain.getClimbingRecords().add(this);
    }

    @Override
    public String toString() {
        return "ClimbingRecord{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user.getId() +
                ", mountain=" + mountain.getId() +
                '}';
    }

}
