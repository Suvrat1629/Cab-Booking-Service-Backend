package com.example.Cab.Booking.dto;

import com.example.Cab.Booking.domain.UserRole;
import com.example.Cab.Booking.model.Vehicle;
import lombok.Data;

@Data
public class DriverDto {

    private Integer id;
    private String name;
    private String email;
    private String mobile;
    private double rating;
    private double latitude;
    private double longitude;
    private UserRole role;
    private Vehicle vehicle;
}
