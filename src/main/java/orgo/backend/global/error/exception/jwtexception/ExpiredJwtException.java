package orgo.backend.global.error.exception.jwtexception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class ExpiredJwtException extends JwtException {
    private final ErrorCode errorCode = ErrorCode.INVALID_JWT;

    public ExpiredJwtException(String message) {
        super(message);
    }
}
