package com.example.Cab.Booking.repository;

import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    public Driver findByEmail(String email);
    
    @Query("SELECT R FROM Ride R WHERE R.status=REQUESTED AND R.driver.id=:driverId")
    public List<Ride> getAllocatedRides(@Param("driverId") Integer driverId);
    
    @Query("SELECT R FROM Ride R WHERE R.status=COMPLETED AND R.driver.Id=:driverId")
    public List<Ride> getCompletedRides(@Param("driverId") Integer driverId);

}
