package orgo.backend.domain._3mountain.entity;

import jakarta.persistence.*;
import lombok.*;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.global.config.jpa.BaseTimeEntity;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Mountain extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Column(length = 1000)
    String description;
    String address;
    String contact;
    String mainImage;
    String backgroundImage;
    String requiredTime;
    @Enumerated(value = EnumType.STRING)
    Difficulty difficulty;
    @Embedded
    Location location;
    @Embedded
    FeatureTag featureTag;
    @OneToMany(mappedBy = "mountain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Peak> peaks;
    @OneToMany(mappedBy = "mountain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClimbingRecord> climbingRecords;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Mountain mountain = (Mountain) object;
        return Objects.equals(id, mountain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
