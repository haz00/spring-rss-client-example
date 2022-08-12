package com.github.haz00.rssfeed;

import com.github.haz00.rssfeed.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException e) {
        return ResponseEntity.badRequest().body(ApiError.of(e.getMessage()));
    }
}
