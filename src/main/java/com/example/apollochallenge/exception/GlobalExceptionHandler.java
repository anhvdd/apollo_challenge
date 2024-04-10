package com.example.apollochallenge.exception;

import com.example.apollochallenge.controller.CustomerController;
import com.example.apollochallenge.dto.response.BaseResponse;
import com.example.apollochallenge.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @ExceptionHandler({ApplicationCustomException.class})
    public ResponseEntity<?> handleApplicationCustomException(
        Exception ex){
        log.error("ApplicationException: {}", ex.getMessage());

        ErrorResponse errorResponse = getErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.toError(errorResponse, ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());

        ErrorResponse errorResponse = getErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.toError(errorResponse, ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(
        Exception ex){
        log.error("Exception: {}", ex.getMessage());

        ErrorResponse errorResponse = getErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.toError(errorResponse, ex.getMessage()));
    }

    private ErrorResponse getErrorResponse(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now().toString());
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        return errorResponse;
    }
}
