package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class OrgoJwtException extends RuntimeException{
    private final ErrorCode errorCode;

    public OrgoJwtException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
