package orgo.backend.domain._4climbingRecord.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import orgo.backend.domain._3mountain.entity.Mountain;
import orgo.backend.domain._4climbingRecord.dto.Position;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;

@Mapper
public interface PositionMapper {
    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    Position toPostion(UserPosDto userPosDto);

    @Mappings({
            @Mapping(source = "mountain.location.longitude",  target = "longitude"),
            @Mapping(source = "mountain.location.latitude", target = "latitude"),
            @Mapping(source = "mountain.location.altitude", target = "altitude")
    })
    Position toPostion(Mountain mountain);
}
