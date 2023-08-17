package orgo.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationErrorResponseDto {
    String code;
    String message;
    List<FieldErrorDto> errorList;

    public ValidationErrorResponseDto(ErrorCode errorCode, List<FieldErrorDto> errors){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errorList = errors;
    }
}
