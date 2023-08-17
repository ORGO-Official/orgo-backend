package orgo.backend.global.error.exception.jwtexception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class CustomJwtException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomJwtException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
