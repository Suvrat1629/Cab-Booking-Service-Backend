package com.example.Cab.Booking.model;

import com.example.Cab.Booking.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String mobile;
    private double rating;
    private double latitude;
    private double longitude;

    private UserRole role;

    private String password;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL,orphanRemoval = true)
    private License license;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ride> rides;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vehicle vehicle;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Ride currentRide;

    private Integer totalRevenue;
}
