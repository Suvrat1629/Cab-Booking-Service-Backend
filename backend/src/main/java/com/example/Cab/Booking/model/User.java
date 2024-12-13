package com.example.Cab.Booking.model;

import com.example.Cab.Booking.domain.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String fullName;

    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    private String profilePicture;

    private UserRole role;
}
