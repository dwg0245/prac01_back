package com.example.demo.common.exception;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.common.model.BaseResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 어디에서든 예외를 던지면 발생하는 예외가 전부 이쪽으로 온다.
// 컨트롤러 까지만 해당이 된다. 필터는 따로 해줘야 한다.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.fail(BaseResponseStatus.REQUEST_ERROR, errors)
        );
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleException(BaseException e) {
        BaseResponseStatus status = e.getStatus();
        int errorCode = status.getCode();
        int statusCode = statusCodeMapper(errorCode);
        BaseResponse response = BaseResponse.fail(status);

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    private int statusCodeMapper(int errorCode) {
        if (errorCode > 3000) {
            return 400;
        } else if (errorCode >= 5000) {
            return 500;
        }

        return 400;
    }
}