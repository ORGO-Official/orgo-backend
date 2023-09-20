package orgo.backend.domain._5badge.entity.acquisition;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.global.config.jpa.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
public class Acquisition extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Badge badge;

    @ManyToOne
    @JoinColumn
    private User user;

    public Acquisition(Badge badge, User user){
        this.badge = badge;
        this.user = user;
    }
}
