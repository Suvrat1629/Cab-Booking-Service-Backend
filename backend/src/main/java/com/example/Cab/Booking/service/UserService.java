package com.example.Cab.Booking.service;

import com.example.Cab.Booking.exception.UserException;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface UserService {

//    public User createUser(User user) throws UserException;
    public User getReqUserProfile(String token) throws UserException;
    public User findUserById(Integer id) throws UserException;

//    public User findUserByEmail(String email) throws UserException;
    public List<Ride> completedRides(Integer userId) throws UserException;
}
