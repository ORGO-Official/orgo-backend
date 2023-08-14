package orgo.backend.domain._3mountain.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FeatureTag {
    boolean goodNightView;
    int totalCourse;
    boolean parkingLot;
    boolean restRoom;
    boolean cableCar;
}
