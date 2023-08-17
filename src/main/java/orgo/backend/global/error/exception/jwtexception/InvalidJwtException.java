package orgo.backend.global.error.exception.jwtexception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class InvalidJwtException extends JwtException {
    private final ErrorCode errorCode = ErrorCode.INVALID_JWT;

    public InvalidJwtException(String message) {
        super(message);
    }
}
