package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import orgo.backend.domain._4climbingRecord.entity.ClimbingRecord;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ClimbingRecordDto {
    private Long id;
    private Long mountainId;
    private String mountainName;
    private LocalDateTime date;
<<<<<<< HEAD
    private double altitude;
    private Long climbingOrder;

    public void setClimbingOrder(Long climbingOrder) {
        this.climbingOrder = climbingOrder;
=======

    public ClimbingRecordDto(ClimbingRecord climbingRecord){
        this.id = climbingRecord.getId();
        this.mountainId = climbingRecord.getMountain().getId();
        this.mountainName = climbingRecord.getMountain().getName();
        this.date = climbingRecord.getDate();
>>>>>>> c14b908872a635fadddfaed06ec90e3b2b25e1f0
    }
}
