package orgo.backend.domain._4climbingRecord.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import orgo.backend.domain._4climbingRecord.domain.ClimbingRecord;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;

import java.util.List;

@Mapper
public interface ClimbingRecordMapper {
    ClimbingRecordMapper INSTANCE = Mappers.getMapper(ClimbingRecordMapper.class);

    @Mappings({
            @Mapping(source = "mountain.id",  target = "mountainId"),
            @Mapping(source = "mountain.name", target = "mountainName"),
            @Mapping(source = "mountain.location.altitude", target = "altitude")
    })
    ClimbingRecordDto toClimbingRecordDto(ClimbingRecord climbingRecord);

    List<ClimbingRecordDto> toClimbingRecordDtoList(List<ClimbingRecord> climbingRecord);
}
