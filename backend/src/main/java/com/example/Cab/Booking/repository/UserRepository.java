package com.example.Cab.Booking.repository;

import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    @Query("Select r from Ride r where r.status=COMPLETED AND r.user.id=:userId")
    public List<Ride> getCompletedRides(@Param("userId") Integer userId);
}
