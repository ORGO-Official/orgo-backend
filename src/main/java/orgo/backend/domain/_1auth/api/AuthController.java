package orgo.backend.domain._1auth.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import orgo.backend.domain._1auth.application.AuthService;
import orgo.backend.domain._1auth.domain.ServiceToken;
import orgo.backend.global.constant.Header;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PermitAll
    @PostMapping("/auth/login/{loginType}")
    public ResponseEntity<ServiceToken> login(@RequestHeader(name = Header.SOCIAL) String socialToken, @PathVariable String loginType) {
        ServiceToken token = authService.login(socialToken, loginType);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader(name = Header.AUTH) String accessToken, @AuthenticationPrincipal Long userId) {
        authService.logout(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/auth/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader(name = Header.SOCIAL) String socialToken, @RequestHeader(name = Header.AUTH) String accessToken, @AuthenticationPrincipal Long userId) {
        authService.withdraw(socialToken, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
