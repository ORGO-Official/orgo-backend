package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class MountainNotFoundException extends OrgoException {
    private final ErrorCode errorCode;

    public MountainNotFoundException() {
        super();
        errorCode = ErrorCode.MOUNTAIN_NOT_FOUND;
    }
}
