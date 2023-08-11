package orgo.backend.domain._1auth.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
