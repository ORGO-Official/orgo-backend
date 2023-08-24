package orgo.backend.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import orgo.backend.global.error.exception.OrgoException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationErrorResponseDto> handle(BindException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        e.printStackTrace();

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(new FieldErrorDto(field, message));
        });
        log.error("ExceptionAdvice - BindException 예외 발생");
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handle(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        e.printStackTrace();

        List<FieldErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(new FieldErrorDto(field, message));
        });
        log.error("ExceptionAdvice - MethodArgumentNotValidException 예외 발생");
        return new ResponseEntity<>(new ValidationErrorResponseDto(errorCode, errors), errorCode.getHttpStatus());
    }

    @ExceptionHandler(InterruptedException.class)
    protected ResponseEntity<ErrorResponseDto> handle(InterruptedException e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getHttpStatus());
    }

    @ExceptionHandler(OrgoException.class)
    protected ResponseEntity<ErrorResponseDto> handle(OrgoException e) {
        ErrorCode errorCode = e.getErrorCode();
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getHttpStatus());
    }
}