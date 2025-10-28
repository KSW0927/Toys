package com.demo.toy.common.exception;

import com.demo.toy.common.response.ResponseResult;

public class BusinessException extends RuntimeException {

    private final ResponseResult result;

    public BusinessException(ResponseResult result) {
        super(result.getMessage());
        this.result = result;
    }

    public ResponseResult getResult() {
        return result;
    }
}
