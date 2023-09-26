package orgo.backend.domain._4climbingRecord.mapper;

import javax.annotation.processing.Generated;
import orgo.backend.domain._3mountain.entity.Location;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.dto.Position;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T13:23:00+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class PositionMapperImpl implements PositionMapper {

    @Override
    public Position toPostion(UserPosDto userPosDto) {
        if ( userPosDto == null ) {
            return null;
        }

        Position.PositionBuilder position = Position.builder();

        position.latitude( userPosDto.getLatitude() );
        position.longitude( userPosDto.getLongitude() );
        position.altitude( userPosDto.getAltitude() );

        return position.build();
    }

    @Override
    public Position toPostion(Mountain mountain) {
        if ( mountain == null ) {
            return null;
        }

        Position.PositionBuilder position = Position.builder();

        position.longitude( mountainLocationLongitude( mountain ) );
        position.latitude( mountainLocationLatitude( mountain ) );
        position.altitude( mountainLocationAltitude( mountain ) );

        return position.build();
    }

    private double mountainLocationLongitude(Mountain mountain) {
        if ( mountain == null ) {
            return 0.0d;
        }
        Location location = mountain.getLocation();
        if ( location == null ) {
            return 0.0d;
        }
        double longitude = location.getLongitude();
        return longitude;
    }

    private double mountainLocationLatitude(Mountain mountain) {
        if ( mountain == null ) {
            return 0.0d;
        }
        Location location = mountain.getLocation();
        if ( location == null ) {
            return 0.0d;
        }
        double latitude = location.getLatitude();
        return latitude;
    }

    private double mountainLocationAltitude(Mountain mountain) {
        if ( mountain == null ) {
            return 0.0d;
        }
        Location location = mountain.getLocation();
        if ( location == null ) {
            return 0.0d;
        }
        double altitude = location.getAltitude();
        return altitude;
    }
}
