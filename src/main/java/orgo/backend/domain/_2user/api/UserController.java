package orgo.backend.domain._2user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orgo.backend.domain._2user.application.UserService;
import orgo.backend.domain._2user.dto.UserProfile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/profile")
    public ResponseEntity<UserProfile> getProfile(@AuthenticationPrincipal Long userId){
        UserProfile profile = userService.getProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
