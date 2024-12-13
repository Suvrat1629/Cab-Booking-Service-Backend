package com.example.Cab.Booking.model;

import com.example.Cab.Booking.domain.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.SqlReturnType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @JsonIgnore
    private List<Integer> declinedDrivers = new ArrayList<>();

    private double pickupLatitude;

    private double pickupLongitude;

    private double destinationLatitude;

    private double destinationLongitude;

    private String pickupArea;

    private String destinationArea;

    private double distance;

    private long duration;

    private RideStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double fare;

    private int otp;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();
}
