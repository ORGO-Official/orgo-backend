package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class InternalServerException extends OrgoException {
    private final Exception originalException;
    private final ErrorCode errorCode;

    public InternalServerException(Exception originalException) {
        super();
        this.originalException = originalException;
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
