package com.example.apollochallenge.exception;

public class ApplicationCustomException extends RuntimeException {
    public ApplicationCustomException(String message) {
        super(message);
    }
}
