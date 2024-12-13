package com.example.Cab.Booking.service;

import com.example.Cab.Booking.exception.RideException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.request.RideRequest;

public interface RideService {

    public Ride requestRide(RideRequest rideRequest, User user) throws Exception;

    public Ride createdRideRequest(User user, Driver nearestDriver,
                                   double pickupLatitude, double pickupLongitude,
                                   double destLatitude, double destLongitude,
                                   String pickupArea, String destArea);

    public void acceptRide(Integer rideId) throws RideException;

    public void declineRide(Integer rideId, Integer driverId) throws RideException;

    public void startRide(Integer rideId, int otp) throws RideException;

    public void completeRide(Integer rideId) throws RideException;

    public void cancelRide(Integer rideId) throws RideException;

    public Ride findByRide(Integer rideId) throws RideException;
}
