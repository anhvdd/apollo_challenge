package com.example.apollochallenge.exception;

import com.example.apollochallenge.controller.CustomerController;
import com.example.apollochallenge.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @ExceptionHandler({ApplicationCustomException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
        Exception ex){
        log.error("Exception ApplicationException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now().toString());
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
