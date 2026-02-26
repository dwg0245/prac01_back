package com.example.demo.common.exception;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.common.model.BaseResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 어디에서든 예외를 던지면 발생하는 예외가 전부 이쪽으로 온다.
// 컨트롤러 까지만 해당이 된다. 필터는 따로 해줘야 한다.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handleException(BaseException e){
        BaseResponseStatus status = e.getStatus();
        int errorCode = status.getCode();
        int statusCode = statusCodeMapper(errorCode);
        BaseResponse response = BaseResponse.fail(status);

        System.out.println("====예외 처리====");
        System.out.println(e.getMessage());
        System.out.println("===============");

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    private int statusCodeMapper(int errorCode){
        if(errorCode > 3000){
            return 400;
        } else if (errorCode >= 5000) {
            return 500;
        }
     return 400;
    }
}
