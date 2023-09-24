package orgo.backend.global.error.exception;

import orgo.backend.global.error.ErrorCode;

public class JwtWrongFormatException extends OrgoException{

    private final ErrorCode errorCode;

    public JwtWrongFormatException() {
        super();
        errorCode = ErrorCode.WRONG_FORMAT_JWT;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
