package orgo.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(8001, "유효성 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    // 9000 ~ : 서버 에러
    INTERNAL_SERVER_ERROR(9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}