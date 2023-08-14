package orgo.backend.domain._3mountain.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Mountain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String address;
    String contact;
    @Enumerated(value = EnumType.STRING)
    Difficulty difficulty;
    @Embedded
    Location location;
    @Embedded
    FeatureTag featureTag;
}
