package orgo.backend.domain._4climbingRecord.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import orgo.backend.domain._3mountain.domain.Mountain;
import orgo.backend.domain._4climbingRecord.domain.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T15:28:28+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
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
}
