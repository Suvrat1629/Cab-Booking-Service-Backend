package com.example.Cab.Booking.response;

import com.example.Cab.Booking.domain.UserRole;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

@Data
public class JwtResponse {

    private String jwt;
    private String message;
    private boolean isAuthenticated;
    private boolean isError;
    private String errorDetails;
    private UserRole type;
}
