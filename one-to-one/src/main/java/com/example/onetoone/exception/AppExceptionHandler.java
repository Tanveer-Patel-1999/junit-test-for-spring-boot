package com.example.onetoone.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> handlePersonNotFound(PersonNotFoundException exception, WebRequest webRequest)
    {
        ApiError apiError = new ApiError(exception.getMessage(),LocalDateTime.now(),webRequest.getDescription(false));
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

}
