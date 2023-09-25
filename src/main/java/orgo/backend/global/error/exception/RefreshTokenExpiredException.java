package orgo.backend.global.error.exception;

import orgo.backend.global.error.ErrorCode;

public class RefreshTokenExpiredException extends OrgoException{
    private final ErrorCode errorCode;

    public RefreshTokenExpiredException() {
        super();
        this.errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
