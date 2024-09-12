package com.example.gccoffee.apiResponse.code.status;


import com.example.gccoffee.apiResponse.code.BaseErrorCode;
import com.example.gccoffee.apiResponse.code.ErrorReasonDTO;
import org.springframework.http.HttpStatus;



public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 유저 관련 에러
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "MEMBER4001", "로그인에 실패했습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4002", "이미 존재하는 이메일 입니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST,"MEMBER4003", "유효하지 않은 이름입니다."),
    WEAK_PASSWORD(HttpStatus.BAD_REQUEST,"MEMBER4004","비밀번호 길이를 맞춰주세요."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,"MEMBER4005","허용되지 않은 권한입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"MEMBER4006","유효하지 않은 토큰입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4007", "사용자가 없습니다."),


    // 상품 관련 에러
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST,"PRODUCT4001","존재하지 않는 상품입니다."),
    PRODUCT_REGISTER_FAIL(HttpStatus.CONFLICT,"PRODUCT4002","상품 등록에 실패했습니다."),
    PRODUCT_UPDATE_FAIL(HttpStatus.CONFLICT,"PRODUCT4003","상품 수정이 실패했습니다."),
    PRODUCT_DELETE_FAIL(HttpStatus.CONFLICT,"PRODUCT4004","상품 삭제가 실패했습니다."),


    // 주문 관련 에러
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST,"ORDER4001","존재하지 않는 주문입니다."),
    ORDER_INGQUIRY_FAIL(HttpStatus.BAD_REQUEST,"ORDER4002","주문 조회에 실패했습니다."),
    ORDER_CANCEL_FAIL(HttpStatus.BAD_REQUEST,"ORDER4003","주문 취소가 실패했습니다."),
    ORDER_STATUS_UPDATE_FAIL(HttpStatus.CONFLICT,"ORDER4004","주문 상태 수정이 실패했습니다."),
    ORDER_DELETE_FAIL(HttpStatus.CONFLICT,"ORDER4005","주문 삭제가 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    // 생성자
    private ErrorStatus(HttpStatus httpStatus, String code, String message) {
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
    public ErrorReasonDTO getReason() {
        return new ErrorReasonDTO(message, code, false);
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return new ErrorReasonDTO(message, code, false, httpStatus);
    }
}
