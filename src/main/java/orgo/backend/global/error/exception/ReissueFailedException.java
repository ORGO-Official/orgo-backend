package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class ReissueFailedException extends OrgoException {
    private final ErrorCode errorCode;

    public ReissueFailedException() {
        super();
        errorCode = ErrorCode.REISSUE_FAILED;
    }
}
