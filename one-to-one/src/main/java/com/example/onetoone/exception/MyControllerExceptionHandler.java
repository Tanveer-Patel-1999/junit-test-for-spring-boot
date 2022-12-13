package com.example.onetoone.exception;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException exception, WebRequest webRequest)
    {
        ApiError errorDetail = new ApiError(exception.getMessage(), LocalDateTime.now(), webRequest.getDescription(false));
       return new ResponseEntity(errorDetail, org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_NOT_FOUND));
    }

}


