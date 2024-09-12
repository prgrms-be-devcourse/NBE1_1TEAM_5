package com.example.gccoffee.apiResponse.exception;


import com.example.gccoffee.apiResponse.code.BaseErrorCode;
import com.example.gccoffee.apiResponse.code.ErrorReasonDTO;

public class GeneralException extends RuntimeException {

    private BaseErrorCode code;
    private String sourceClass;
    private String sourceMethod;
    private String sourcePackage;
    private String sourceAddress;

    public GeneralException(BaseErrorCode errorCode) {
        extractSourceInfo();
        this.code = errorCode;
    }

    // Getter 메서드들
    public BaseErrorCode getCode() {
        return code;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public String getSourceMethod() {
        return sourceMethod;
    }

    public String getSourcePackage() {
        return sourcePackage;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }

    private void extractSourceInfo() {
        StackTraceElement[] stackTrace = this.getStackTrace();
        if (stackTrace.length > 1) {
            StackTraceElement element = stackTrace[1];  // [0]는 현재 생성자, [1]은 CustomException을 발생시킨 메서드
            this.sourceClass = element.getClassName();
            this.sourceMethod = element.getMethodName();

            // 패키지 정보는 클래스 이름에서 추출
            int lastDotIndex = this.sourceClass.lastIndexOf('.');
            if (lastDotIndex > 0) {
                this.sourcePackage = this.sourceClass.substring(0, lastDotIndex);
            } else {
                this.sourcePackage = "Unknown";
            }
        }
        this.sourceAddress = sourcePackage + "." + sourceClass + "." + sourceMethod;
    }
}
