package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class InternalServerException extends OrgoException {
    private final ErrorCode errorCode;

    public InternalServerException() {
        super();
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
