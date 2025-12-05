package com.signal.web.exception;

import com.signal.web.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("서버 에러 발생: " + e.getMessage());
    }
}
