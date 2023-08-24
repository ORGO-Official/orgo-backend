package orgo.backend.domain._4climbingRecord.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import orgo.backend.domain._4climbingRecord.application.ClimbingRecordService;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClimbingRecordController {

    private final ClimbingRecordService climbingRecordService;

    @PostMapping("climbingrecords")
    public ResponseEntity<Void> registerClimbingRecords(@AuthenticationPrincipal Long userId, @RequestBody UserPosDto userPosDto) {
        try {
            climbingRecordService.registerClimbingRecord(userId, userPosDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/climbingrecords")
    public ResponseEntity<List<ClimbingRecordDto>> viewClimbingRecords(@PathVariable Long userId) {
        return new ResponseEntity<>(climbingRecordService.viewMyClimbingRecords(userId), HttpStatus.OK);
    }
}
