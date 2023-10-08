package orgo.backend.domain._4climbingRecord.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import orgo.backend.domain._3mountain.entity.Location;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-05T10:17:43+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
public class ClimbingRecordMapperImpl implements ClimbingRecordMapper {

    @Override
    public ClimbingRecordDto toClimbingRecordDto(ClimbingRecord climbingRecord) {
        if ( climbingRecord == null ) {
            return null;
        }

        ClimbingRecordDto.ClimbingRecordDtoBuilder climbingRecordDto = ClimbingRecordDto.builder();

        climbingRecordDto.mountainId( climbingRecordMountainId( climbingRecord ) );
        climbingRecordDto.mountainName( climbingRecordMountainName( climbingRecord ) );
        climbingRecordDto.altitude( climbingRecordMountainLocationAltitude( climbingRecord ) );
        climbingRecordDto.id( climbingRecord.getId() );
        climbingRecordDto.date( climbingRecord.getDate() );

        return climbingRecordDto.build();
    }

    @Override
    public List<ClimbingRecordDto> toClimbingRecordDtoList(List<ClimbingRecord> climbingRecord) {
        if ( climbingRecord == null ) {
            return null;
        }

        List<ClimbingRecordDto> list = new ArrayList<ClimbingRecordDto>( climbingRecord.size() );
        for ( ClimbingRecord climbingRecord1 : climbingRecord ) {
            list.add( toClimbingRecordDto( climbingRecord1 ) );
        }

        return list;
    }

    private Long climbingRecordMountainId(ClimbingRecord climbingRecord) {
        if ( climbingRecord == null ) {
            return null;
        }
        Mountain mountain = climbingRecord.getMountain();
        if ( mountain == null ) {
            return null;
        }
        Long id = mountain.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String climbingRecordMountainName(ClimbingRecord climbingRecord) {
        if ( climbingRecord == null ) {
            return null;
        }
        Mountain mountain = climbingRecord.getMountain();
        if ( mountain == null ) {
            return null;
        }
        String name = mountain.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private double climbingRecordMountainLocationAltitude(ClimbingRecord climbingRecord) {
        if ( climbingRecord == null ) {
            return 0.0d;
        }
        Mountain mountain = climbingRecord.getMountain();
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
