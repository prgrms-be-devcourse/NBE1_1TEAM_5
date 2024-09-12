package com.example.gccoffee.apiResponse.exception.handler;

import com.example.gccoffee.apiResponse.code.BaseErrorCode;
import com.example.gccoffee.apiResponse.exception.GeneralException;

public class ErrorHandler extends GeneralException {

    public ErrorHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
