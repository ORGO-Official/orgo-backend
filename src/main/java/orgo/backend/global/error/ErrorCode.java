package orgo.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("9999", "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND("0001", "회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("0002", "유효성 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    SOCIAL_TOKEN_EXPIRED("0003", "소셜 토큰이 만료되었습니다. 재발급해주세요.", HttpStatus.BAD_REQUEST),
    CONNECTION_FAILED("0004", "외부 API 통신 중에 오류가 발생헀습니다. ", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("0005", "해당 리소스를 찾을 수 없습니다. ", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}