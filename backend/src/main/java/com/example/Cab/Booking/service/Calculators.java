package com.example.Cab.Booking.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class Calculators {

    private static final int EARTH_RADIUS = 6371;

    public double calculateDistance(double sourceLatitude, double sourceLongitude, double destLatitude, double destLongitude){
        double distLat = Math.toRadians(destLatitude - sourceLatitude);
        double distLong = Math.toRadians(destLongitude - sourceLongitude);

        double a = Math.sin(destLatitude/2) * Math.sin(destLatitude/2)
                + Math.cos(Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destLatitude))
                + Math.sin(distLong/2) * Math.sin(distLong/2);
        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double distance = EARTH_RADIUS*c;
        return distance;
    }

    public long calculateDuration(LocalDateTime startTime, LocalDateTime endTime){
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }

    public double calculateFare(double distance){
        double baseFare = 11;
        double totalFare = baseFare * distance;
        return totalFare;
    }
}
