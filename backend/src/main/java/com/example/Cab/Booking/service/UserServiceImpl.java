package com.example.Cab.Booking.service;

import com.example.Cab.Booking.Config.JwtProvider;
import com.example.Cab.Booking.exception.UserException;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public User getReqUserProfile(String token) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(email);

        if(user!=null){
            return user;
        }

        throw new UserException("invalid token...");
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> opt = userRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new UserException("user not found with id " + id);
    }

    @Override
    public List<Ride> completedRides(Integer userId) throws UserException {
        List<Ride> completedRides = userRepository.getCompletedRides(userId);
        return completedRides;
    }
}
