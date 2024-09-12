package com.example.gccoffee.apiResponse.code.status;


import com.example.gccoffee.apiResponse.code.BaseCode;
import com.example.gccoffee.apiResponse.code.ReasonDTO;
import org.springframework.http.HttpStatus;


public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다.");

    // 멤버 관련 응답

    // ~~~ 관련 응답

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    // 생성자
    private SuccessStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    // Getter 메서드
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public ReasonDTO getReason() {
        return new ReasonDTO(message, code, true);
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return new ReasonDTO(message, code, true, httpStatus);
    }
}
