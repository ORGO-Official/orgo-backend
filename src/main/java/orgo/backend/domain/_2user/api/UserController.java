package orgo.backend.domain._2user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import orgo.backend.domain._2user.application.UserService;
import orgo.backend.domain._2user.dto.UserProfileDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/profile")
    public ResponseEntity<UserProfileDto.Response> getProfile(@AuthenticationPrincipal Long userId) {
        UserProfileDto.Response profile = userService.getProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/users/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal Long userId, @RequestPart MultipartFile imageFile, @RequestPart UserProfileDto.Request requestDto) {
        userService.updateProfile(userId, requestDto, imageFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
