package orgo.backend.domain._5badge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
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

    @Enumerated(value = EnumType.STRING)
    protected BadgeGroup mainGroup;

    protected String objective;

    protected String description;

    public Badge(BadgeGroup mainGroup, String objective, String description){
        this.mainGroup = mainGroup;
        this.objective = objective;
        this.description = description;
    }

    /**
     * 뱃지를 발급 가능한지 확인합니다.
     *
     * @param object 메서드 호출에 필요한 파라미터
     * @return 뱃지 발급 가능 여부
     */
    public abstract boolean check(Object object);

    /**
     * Acquisition 엔티티를 생성합니다.
     *
     * @param user 사용자
     * @return 해당 뱃지의 획득 정보
     */
    public abstract Acquisition issue(User user);
}
