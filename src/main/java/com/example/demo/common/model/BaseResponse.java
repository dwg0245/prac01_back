package com.example.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.demo.common.model.BaseResponseStatus.SUCCESS;
// 응답 코딩 컨텐션
@Getter
@Setter
@AllArgsConstructor
public class BaseResponse <T>{
    private Boolean success; //
    private Integer code;
    private String message; // 백엔드에서 관리해도 되고 프론트에서 해도 되고 / 예외 메세지 띄우기
    private T result;

    public static <T> BaseResponse success(T result) {
        return new BaseResponse(
                SUCCESS.isSuccess(),
                SUCCESS.getCode(),
                SUCCESS.getMessage(),
                result);
    }

    public static <T> BaseResponse fail(BaseResponseStatus status) {
        return new BaseResponse(
                status.isSuccess(),
                status.getCode(),
                status.getMessage(),
                null);
    }

    public static <T> BaseResponse fail(BaseResponseStatus status,T result) {
        return new BaseResponse(
                status.isSuccess(),
                status.getCode(),
                status.getMessage(),
                result);
    }


}
