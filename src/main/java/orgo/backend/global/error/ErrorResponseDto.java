package orgo.backend.global.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int status;
    int code;
    String name;
    String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.getCode();
        this.name = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public ErrorResponseDto(ErrorCode errorCode, String message){
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.getCode();
        this.name = errorCode.name();
        this.message = errorCode.getMessage() + " " + message;
    }

    public ErrorResponseDto(String errorCode, String message){
        this.status = 403;
        this.code = Integer.parseInt("61" + errorCode.substring("CF-".length()));
        this.name = "CODEF_ERROR";
        this.message = message;
    }
}
