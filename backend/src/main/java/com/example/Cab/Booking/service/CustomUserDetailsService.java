package com.example.Cab.Booking.service;

import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.repository.DriverRepository;
import com.example.Cab.Booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();

        User user = userRepository.findByEmail(username);

        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
        }

        Driver driver = driverRepository.findByEmail(username);

        if(driver!=null){
            return new org.springframework.security.core.userdetails.User(driver.getEmail(),driver.getPassword(),authorities);
        }

        throw new UsernameNotFoundException("User not found with email: -" + username);
    }
}
