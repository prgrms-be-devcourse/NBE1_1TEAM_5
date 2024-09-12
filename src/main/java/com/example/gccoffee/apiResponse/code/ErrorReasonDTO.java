package com.example.gccoffee.apiResponse.code;


import org.springframework.http.HttpStatus;


public class ErrorReasonDTO {

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

    // 생성자
    public ErrorReasonDTO(String message, String code, boolean isSuccess) {
        this(message, code, isSuccess, null); // httpStatus는 기본값으로 null
    }

    public ErrorReasonDTO(String message, String code, boolean isSuccess, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    // Getter 메서드
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
