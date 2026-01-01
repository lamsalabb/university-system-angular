package com.university.core.exception;

import com.university.attendance.exception.AttendanceNotFoundException;
import com.university.fee.exception.FeeNotFoundException;
import com.university.fee.exception.OutstandingFeesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFound(EmailNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFound(CourseNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<?> handleEnrollmentNotFound(EnrollmentNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<?> handleAttendanceNotFoundException(AttendanceNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(FeeNotFoundException.class)
    public ResponseEntity<?> handleFeeNotFoundException(FeeNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException e){
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    public ResponseEntity<?> handleCourseAlreadyExists(CourseAlreadyExistsException e){
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException e){
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(EnrollmentAlreadyExistsException.class)
    public ResponseEntity<?> handleEnrollmentAlreadyExists(EnrollmentAlreadyExistsException e){
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(NonStudentEnrollmentException.class)
    public ResponseEntity<?> handleNonStudentEnrollment(NonStudentEnrollmentException e){
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    }

    @ExceptionHandler(OutstandingFeesException.class)
    public ResponseEntity<?> handleOutstandingFees(OutstandingFeesException e){
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    }


    @ExceptionHandler(Exception.class)//fallback for any generic exceptions
    public ResponseEntity<?> handleGenericException(Exception e){
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred.");
    }





    //helper class to build a response
    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorBody = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message",message
        );

        return new ResponseEntity<>(errorBody,status);
    }


}
