package orgo.backend.domain._5badge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.global.config.jpa.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "subGroup")
public abstract class Badge extends BaseTimeEntity {
    @Id
    @GeneratedValue
    protected Long id;
    protected BadgeGroup mainGroup;

    public Badge(BadgeGroup mainGroup){
        this.mainGroup = mainGroup;
    }

    public abstract void issue(User user);
}
