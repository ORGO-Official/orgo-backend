package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ClimbingRecordDto {
    private Long id;
    private Long mountainId;
    private String mountainName;
    private LocalDateTime date;
    private double altitude;
    private Long climbingOrder;

    public void setClimbingOrder(Long climbingOrder) {
        this.climbingOrder = climbingOrder;
    }
}
