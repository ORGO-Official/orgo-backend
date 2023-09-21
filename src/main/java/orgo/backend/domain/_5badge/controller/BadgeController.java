package orgo.backend.domain._5badge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import orgo.backend.domain._5badge.dto.BadgeDto;
import orgo.backend.domain._5badge.service.BadgeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/badges/acquired")
    public ResponseEntity<List<BadgeDto.Acquired>> getAcquiredBadges(@AuthenticationPrincipal Long userId){
        List<BadgeDto.Acquired> responseDto =  badgeService.getAcquiredBadges(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/badges/not-acquired")
    public ResponseEntity<List<BadgeDto.NotAcquired>> getNotAcquiredBadges(@AuthenticationPrincipal Long userId){
        List<BadgeDto.NotAcquired> responseDto =  badgeService.getNotAcquiredBadges(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
