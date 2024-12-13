package com.example.Cab.Booking.repository;

import com.example.Cab.Booking.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Integer> {
}
