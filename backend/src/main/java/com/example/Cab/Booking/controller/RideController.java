package com.example.Cab.Booking.controller;

import com.example.Cab.Booking.dto.RideDTO;
import com.example.Cab.Booking.exception.DriverException;
import com.example.Cab.Booking.exception.RideException;
import com.example.Cab.Booking.exception.UserException;
import com.example.Cab.Booking.mapper.DtoMapper;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.request.RideRequest;
import com.example.Cab.Booking.request.StartRideRequest;
import com.example.Cab.Booking.response.MessageResponse;
import com.example.Cab.Booking.service.DriverService;
import com.example.Cab.Booking.service.RideService;
import com.example.Cab.Booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    private final DriverService driverService;

    private final UserService userService;

    @PostMapping("/request")
    public ResponseEntity<RideDTO> userRequestHandler(@RequestBody RideRequest rideRequest, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.requestRide(rideRequest, user);

        RideDTO rideDTO = DtoMapper.toRideDto(ride);

        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<MessageResponse> acceptRideHandler(@PathVariable Integer rideId) throws UserException, RideException{

        rideService.acceptRide(rideId);

        MessageResponse res = new MessageResponse("Ride accepted by the driver");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{rideId}/decline")
    public ResponseEntity<MessageResponse> declineRideHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer rideId) throws UserException,RideException, DriverException{

        Driver driver = driverService.getReqDriverProfile(jwt);

        rideService.declineRide(rideId, driver.getId());

        MessageResponse res = new MessageResponse("Ride decline by driver");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<MessageResponse> rideStartHandler(@PathVariable Integer rideId, @RequestBody StartRideRequest req) throws UserException,RideException, DriverException{

        rideService.startRide(rideId, req.getOtp());

        MessageResponse res = new MessageResponse("Ride has Started");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<MessageResponse> rideCompleteHandler(@PathVariable Integer rideId) throws UserException, RideException{

        rideService.completeRide(rideId);

        MessageResponse res = new MessageResponse("Ride is completed thank you for booking");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO> findRideByIdHandler(@PathVariable Integer rideId, @RequestHeader("Authorization") String jwt) throws UserException, RideException {

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.findByRide(rideId);

        RideDTO rideDTO = DtoMapper.toRideDto(ride);

        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }
}
