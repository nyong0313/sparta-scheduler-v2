package org.example.schedulerv2.config;

import org.example.schedulerv2.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
