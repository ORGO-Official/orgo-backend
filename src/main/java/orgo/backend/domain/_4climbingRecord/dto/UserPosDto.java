package orgo.backend.domain._4climbingRecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UserPosDto {
    private Long mountainId;
    private Long latitude;
    private Long longitude;
    private Long altitude;
    private LocalDateTime date;
}
