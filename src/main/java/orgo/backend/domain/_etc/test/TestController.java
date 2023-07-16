package orgo.backend.domain._etc.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<TestResponse> test(@RequestBody TestRequest requestDto) {
        return new ResponseEntity<>(new TestResponse(requestDto.getField()), HttpStatus.OK);
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
