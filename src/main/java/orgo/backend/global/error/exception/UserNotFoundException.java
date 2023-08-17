package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class UserNotFoundException extends OrgoException {
    private final ErrorCode errorCode;

    public UserNotFoundException() {
        super();
        errorCode = ErrorCode.USER_NOT_FOUND;
    }
}
