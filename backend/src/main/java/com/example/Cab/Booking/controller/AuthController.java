package com.example.Cab.Booking.controller;

import com.example.Cab.Booking.Config.JwtProvider;
import com.example.Cab.Booking.domain.UserRole;
import com.example.Cab.Booking.exception.UserException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.repository.DriverRepository;
import com.example.Cab.Booking.repository.UserRepository;
import com.example.Cab.Booking.request.DriverSignupRequest;
import com.example.Cab.Booking.request.LoginRequest;
import com.example.Cab.Booking.request.SignupRequest;
import com.example.Cab.Booking.response.JwtResponse;
import com.example.Cab.Booking.service.CustomUserDetailsService;
import com.example.Cab.Booking.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final DriverService driverService;
    private JwtProvider jwtProvider;
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignupRequest req) throws UserException {

        String email = req.getEmail();
        String fullName = req.getFullName();
        String mobile = req.getMobile();
        String password = req.getPassword();

        User user = userRepository.findByEmail(email);

        if (user!=null){
            throw new UserException("User already exists " + email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(encodedPassword);
        createdUser.setFullName(fullName);
        createdUser.setMobile(mobile);
        createdUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtProvider.getEmailFromJwtToken(String.valueOf(authentication));

        JwtResponse response = new JwtResponse();

        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setErrorDetails(null);
        response.setType(UserRole.USER);
        response.setMessage("Account Created Successfully: " + savedUser.getFullName());

        return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
    }


    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest loginRequest){

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticateAction(password, username);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        JwtResponse response = new JwtResponse();
        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setErrorDetails(null);
        response.setType(UserRole.USER);
        response.setMessage("Account Login Successful");

        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/driver/signup")
    public ResponseEntity<JwtResponse> driverSignUp(@RequestBody DriverSignupRequest driverSignupRequest){

        Driver driver = driverRepository.findByEmail(driverSignupRequest.getEmail());

        JwtResponse response = new JwtResponse();

        if(driver!=null){
            response.setAuthenticated(false);
            response.setErrorDetails("Email is already in use");
            response.setError(true);

            return new ResponseEntity<JwtResponse>(response, HttpStatus.BAD_REQUEST);
        }

        Driver createdDriver = driverService.registerDriver(driverSignupRequest);
        Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(), createdDriver.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setErrorDetails(null);
        response.setType(UserRole.DRIVER);
        response.setMessage("Account Created Successfully: " + createdDriver.getName());

        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticateAction(String password,String username) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails);
    }

}
