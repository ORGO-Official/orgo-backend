package orgo.backend.domain._1auth.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import orgo.backend.domain._1auth.application.AuthService;
import orgo.backend.domain._1auth.domain.ServiceToken;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PermitAll
    @PostMapping("/auth/login/{loginType}")
    public ResponseEntity<ServiceToken> login(@RequestHeader String socialToken, @PathVariable String loginType) {
        ServiceToken token = authService.login(socialToken, loginType);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PermitAll
    @PostMapping("/auth/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader String socialToken, @RequestHeader String accessToken, @AuthenticationPrincipal Long userId) {
        authService.withdraw(userId, socialToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
