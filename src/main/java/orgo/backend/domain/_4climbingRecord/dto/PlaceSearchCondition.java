package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PlaceSearchCondition {
    private final double latitude;
    private final double longitude;
    private final double radius;
}
