package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPosDto {
    private Long mountainId;
    private double latitude;
    private double longitude;
    private double altitude;
    private LocalDateTime date;
}
