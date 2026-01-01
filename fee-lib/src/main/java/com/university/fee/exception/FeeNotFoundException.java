package com.university.fee.exception;

public class FeeNotFoundException extends RuntimeException {
    public FeeNotFoundException(String message) {
        super(message);
    }
}
