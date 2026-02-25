package com.example.demo.common.exception;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.common.model.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;

    public BaseException(BaseResponseStatus status){
        super(status.getMessage()); // 부모에게 지정
        this.status = status;       // 현재 변수에 지정
    }

    public static BaseException from(BaseResponseStatus status){
        return new BaseException(status);
    }
}
