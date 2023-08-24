package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class SocialTokenExpiredException extends OrgoException {
    private final ErrorCode errorCode;

    public SocialTokenExpiredException() {
        super();
        errorCode = ErrorCode.SOCIAL_TOKEN_EXPIRED;
    }
}
