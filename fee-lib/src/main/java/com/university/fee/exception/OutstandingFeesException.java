package com.university.fee.exception;

public class OutstandingFeesException extends RuntimeException {
    public OutstandingFeesException(String message) {
        super(message);
    }
}
