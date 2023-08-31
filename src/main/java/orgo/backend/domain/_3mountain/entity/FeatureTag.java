package orgo.backend.domain._3mountain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureTag {
    boolean goodNightView;
    int totalCourse;
    boolean parkingLot;
    boolean restRoom;
    boolean cableCar;
}
