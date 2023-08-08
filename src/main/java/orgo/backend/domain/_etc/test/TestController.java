package orgo.backend.domain._etc.test;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @PostMapping("/test")
    @PermitAll()
    public ResponseEntity<TestResponse> test(@RequestBody TestRequest requestDto) {
        return new ResponseEntity<>(new TestResponse(requestDto.getField()), HttpStatus.OK);
    }

    @PostMapping("/test/jwt")
    @PermitAll()
    public ResponseEntity<TestResponse> testJwt(@RequestHeader String access, @AuthenticationPrincipal Long userId) {
        log.info(access);
        log.info("my userId = {}", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestRequest {
        String field;
    }

    @Getter
    @AllArgsConstructor
    public static class TestResponse {
        String field;
    }
}