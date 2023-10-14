package orgo.backend.domain._4climbingRecord.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import orgo.backend.domain._4climbingRecord.dto.ClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.MyClimbingRecordDto;
import orgo.backend.domain._4climbingRecord.dto.UserPosDto;
import orgo.backend.domain._4climbingRecord.service.ClimbingRecordService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClimbingRecordController {

    private final ClimbingRecordService climbingRecordService;

    @PostMapping("/climbing-records")
    public ResponseEntity<Void> registerClimbingRecords(@AuthenticationPrincipal Long userId, @RequestBody UserPosDto userPosDto) {
        try {
            climbingRecordService.registerClimbingRecord(userId, userPosDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/climbing-records")
    public ResponseEntity<MyClimbingRecordDto> viewClimbingRecords(@AuthenticationPrincipal Long userId) {
        return new ResponseEntity<>(climbingRecordService.viewMyClimbingRecords(userId), HttpStatus.OK);
    }
}
