package orgo.backend.domain._5badge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.global.config.jpa.BaseTimeEntity;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "subGroup")
@ToString
public abstract class Badge extends BaseTimeEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @Enumerated(value = EnumType.STRING)
    protected BadgeGroup mainGroup;

    protected String objective;

    protected String description;

    public Badge(BadgeGroup mainGroup, String objective, String description) {
        this.mainGroup = mainGroup;
        this.objective = objective;
        this.description = description;
    }

    /**
     * 뱃지를 발급 가능한지 확인합니다.
     *
     * @param user 메서드 호출에 필요한 파라미터
     * @return 뱃지 발급 가능 여부
     */
    public abstract boolean canIssue(User user);

    /**
     * Acquisition 엔티티를 생성합니다.
     * 하위 클래스에서 재정의할 수 없습니다.
     *
     * @param user 사용자
     * @return 해당 뱃지의 획득 정보
     */
    public final Acquisition issue(User user) {
        return new Acquisition(this, user);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Badge badge = (Badge) object;
        return Objects.equals(id, badge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
