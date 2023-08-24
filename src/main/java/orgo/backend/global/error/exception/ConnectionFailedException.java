package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class ConnectionFailedException extends OrgoException {
    private final ErrorCode errorCode;

    public ConnectionFailedException() {
        super();
        errorCode = ErrorCode.CONNECTION_FAILED;
    }
}
