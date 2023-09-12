package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyClimbingRecordDto {
    private double climbedAltitude;
    private Long climbingCnt;
    private List<ClimbingRecordDto> climbingRecordDtoList;

    public void setClimbedAltitudeByMyClimbingRecordList() {
        double climbedAltitude=0;

        for(ClimbingRecordDto climbingRecordDto : this.climbingRecordDtoList) {
            climbedAltitude+=climbingRecordDto.getAltitude();
        }

        this.climbedAltitude=climbedAltitude;
    }

    public void setClimbingCntByMyClimbingRecordList() {
        this.climbingCnt = (long) this.climbingRecordDtoList.size();
    }
}
