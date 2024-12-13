package com.example.Cab.Booking.controller;

import com.example.Cab.Booking.exception.DriverException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/drivers")
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/profile")
    public ResponseEntity<Driver> getReqDriverProfileHandler(@RequestHeader("Authorization") String jwt) throws DriverException {

        Driver driver = driverService.getReqDriverProfile(jwt);
        return new ResponseEntity<Driver>(driver, HttpStatus.OK);
    }

    @GetMapping("/{driverId}/current_ride")
    public ResponseEntity<Ride> getDriversCurrentRideHandler(@PathVariable Integer driverId) throws DriverException{

        Ride ride = driverService.getDriversCurrentRide(driverId);

        return new ResponseEntity<Ride>(ride,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{driverId}/allocated")
    public ResponseEntity<List<Ride>> getAllocatedRidesHandler(@PathVariable Integer driverId) throws DriverException {

        List<Ride> rides = driverService.getAllocatedRide(driverId);

        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);
    }


    public ResponseEntity<List<Ride>> getCompletedRidesHandler(@RequestHeader("Authorization") String jwt) throws DriverException {
        Driver driver = driverService.getReqDriverProfile(jwt);
        List<Ride> rides = driverService.completedRide(driver.getId());

        return new ResponseEntity<>(rides,HttpStatus.ACCEPTED);
    }
}
