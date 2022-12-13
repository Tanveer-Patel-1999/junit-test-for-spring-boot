package com.example.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsumerClientException.class)
    public ResponseEntity<ErrorDetail> ConsumerClientExceptionException(ConsumerClientException exception, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(exception.getMessage(),LocalDateTime.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail( "Something went Wrong...pls try again later",LocalDateTime.now(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


