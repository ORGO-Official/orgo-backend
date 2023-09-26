package orgo.backend.domain._4climbingRecord.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Position {
    private double latitude;
    private double longitude;
    private double altitude;

    //Haversine 공식을 기반으로 두 장소간의 거리를 계산합니다.
    public double calDistanceFrom(Position position) {
        final double RADIUS_OF_EARTH_KM = 6371.0;

        double latitudeRadianDiff = Math.toRadians(position.latitude - this.latitude);
        double longitudeRadianDiff = Math.toRadians(position.longitude - this.longitude);

        double a = Math.sin(latitudeRadianDiff / 2) * Math.sin(latitudeRadianDiff / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(position.latitude)) *
                        Math.sin(longitudeRadianDiff / 2) * Math.sin(longitudeRadianDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS_OF_EARTH_KM * c;
    }
}
