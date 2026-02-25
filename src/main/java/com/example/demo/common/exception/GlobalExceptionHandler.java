package com.example.demo.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 어디에서든 예외를 던지면 발생하는 예외가 전부 이쪽으로 온다.
// 컨트롤러 까지만 해당이 된다. 필터는 따로 해줘야 한다.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public void handleException(Exception e){
        System.out.println("====예외 처리====");
        System.out.println(e.getMessage());
        System.out.println("===============");
    }

}
