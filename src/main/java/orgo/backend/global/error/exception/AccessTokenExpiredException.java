package orgo.backend.global.error.exception;

import orgo.backend.global.error.ErrorCode;

public class AccessTokenExpiredException extends OrgoException{
    private final ErrorCode errorCode;

    public AccessTokenExpiredException() {
        super();
        this.errorCode = ErrorCode.ACCESS_TOKEN_EXPIRED;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
