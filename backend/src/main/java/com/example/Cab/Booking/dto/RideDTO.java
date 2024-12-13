package com.example.Cab.Booking.dto;

import com.example.Cab.Booking.domain.RideStatus;
import com.example.Cab.Booking.model.PaymentDetails;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideDTO {

    private Integer id;
    private UserDto user;
    private DriverDto driver;
    private double pickupLatitude;
    private double pickupLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private String pickupArea;
    private String destinationArea;
    private double distance;
    private long duration;
    private RideStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double fare;
//    private PaymentDetails paymentDetails;
    private int otp;
}
