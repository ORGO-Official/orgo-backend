package orgo.backend.domain._3mountain.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @OneToMany(mappedBy = "mountain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Peak> peaks;
}