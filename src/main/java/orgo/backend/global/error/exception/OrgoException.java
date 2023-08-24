package orgo.backend.global.error.exception;

import orgo.backend.global.error.ErrorCode;

public abstract class OrgoException extends RuntimeException{
    public OrgoException() {
        super();
    }

    abstract public ErrorCode getErrorCode();
}
