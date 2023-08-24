package orgo.backend.domain._4climbingRecord.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.global.config.jpa.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
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
}
