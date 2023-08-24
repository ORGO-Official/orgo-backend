package orgo.backend.global.error.exception;

import lombok.Getter;
import orgo.backend.global.error.ErrorCode;

@Getter
public class ResourceNotFoundException extends OrgoException {
    private final ErrorCode errorCode;

    public ResourceNotFoundException() {
        super();
        errorCode = ErrorCode.RESOURCE_NOT_FOUND;
    }
}
