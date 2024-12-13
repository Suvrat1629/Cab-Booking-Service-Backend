package com.example.Cab.Booking.request;

import com.example.Cab.Booking.model.License;
import com.example.Cab.Booking.model.Vehicle;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

@Data
public class DriverSignupRequest {

    private String name;
    private String email;
    private String mobile;
    private String password;
    private double latitude;
    private double longitude;

    private License license;

    private Vehicle vehicle;
}
