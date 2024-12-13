package com.example.Cab.Booking.exception;

import ch.qos.logback.core.util.Loader;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetail {

    private String error;
    private String details;
    private LocalDateTime timeStamp;

    public ErrorDetail(String error, String details, LocalDateTime timeStamp){
        super();
        this.error = error;
        this.details = details;
        this.timeStamp = timeStamp;
    }
}
