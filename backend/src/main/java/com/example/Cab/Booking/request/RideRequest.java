package com.example.Cab.Booking.request;

import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

@Data
public class RideRequest {

    private double pickupLongitude;
    private double pickupLatitude;
    private double destinationLongitude;
    private double destinationLatitude;
    private String pickupArea;
    private String destinationArea;
}
