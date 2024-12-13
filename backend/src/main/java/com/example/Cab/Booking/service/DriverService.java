package com.example.Cab.Booking.service;

import com.example.Cab.Booking.exception.DriverException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.request.DriverSignupRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DriverService {

    public Driver registerDriver(DriverSignupRequest driverSignupRequest);

    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride);

    public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude);

    public Driver getReqDriverProfile(String jwt) throws DriverException;

    public Ride getDriversCurrentRide(Integer driverId) throws DriverException;

    public List<Ride> getAllocatedRide(Integer driverId) throws DriverException;

    public Driver findDriverId(Integer driverId) throws DriverException;

    public List<Ride> completedRide(Integer driverId) throws DriverException;
}
