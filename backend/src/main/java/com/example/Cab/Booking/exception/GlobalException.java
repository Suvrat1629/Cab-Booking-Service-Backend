package com.example.Cab.Booking.exception;

import jakarta.persistence.ElementCollection;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.autoconfigure.batch.BatchTaskExecutor;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.core.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException ue, WebRequest req){

        ErrorDetail errorDetail = new ErrorDetail(ue.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DriverException.class)
    public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException de,WebRequest req){

        ErrorDetail errorDetail = new ErrorDetail(de.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(RideException.class)
    public ResponseEntity<ErrorDetail> rideExceptionHandler(RideException re, WebRequest req){

        ErrorDetail errorDetail = new ErrorDetail(re.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleValidationException(ConstraintViolationException cve){

        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation: cve.getConstraintViolations()){
            errorMessage.append(violation.getMessage() + "\n");
        }

        ErrorDetail errorDetail = new ErrorDetail(errorMessage.toString(),"Validation Error", LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me){
        ErrorDetail errorDetail = new ErrorDetail(me.getBindingResult().getFieldError().getDefaultMessage(),"validation error",LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e,WebRequest req){

        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.ACCEPTED);
    }
}
