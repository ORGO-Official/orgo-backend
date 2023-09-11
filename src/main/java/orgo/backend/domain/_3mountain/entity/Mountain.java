package orgo.backend.domain._3mountain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;
import orgo.backend.global.config.jpa.BaseTimeEntity;

import java.util.List;

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
}
