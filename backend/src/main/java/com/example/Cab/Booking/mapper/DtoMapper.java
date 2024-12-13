package com.example.Cab.Booking.mapper;

import com.example.Cab.Booking.dto.DriverDto;
import com.example.Cab.Booking.dto.RideDTO;
import com.example.Cab.Booking.dto.UserDto;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;

public class DtoMapper {

    public static DriverDto toDriverDto(Driver driver){

        DriverDto driverDto = new DriverDto();

        driverDto.setEmail(driver.getEmail());
        driverDto.setId(driver.getId());
        driverDto.setLatitude(driver.getLatitude());
        driverDto.setLongitude(driver.getLongitude());
        driverDto.setMobile(driver.getMobile());
        driverDto.setName(driver.getName());
        driverDto.setRating(driver.getRating());
        driverDto.setRole(driver.getRole());
        driverDto.setVehicle(driver.getVehicle());

        return driverDto;
    }

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getFullName());
        userDto.setMobile(user.getMobile());

        return userDto;
    }

    public static RideDTO toRideDto(Ride ride){
        DriverDto driverDto = toDriverDto(ride.getDriver());
        UserDto userDto = toUserDto(ride.getUser());

        RideDTO rideDto = new RideDTO();

        rideDto.setDestinationLatitude(ride.getDestinationLatitude());
        rideDto.setDestinationLongitude(ride.getDestinationLongitude());
        rideDto.setDistance(ride.getDistance());
        rideDto.setDuration(ride.getDuration());
        rideDto.setEndTime(ride.getEndTime());
        rideDto.setFare(ride.getFare());
        rideDto.setId(ride.getId());
        rideDto.setPickupLatitude(ride.getPickupLatitude());
        rideDto.setPickupLongitude(ride.getPickupLongitude());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setOtp(ride.getOtp());
        rideDto.setUser(userDto);
        rideDto.setStatus(ride.getStatus());
//        rideDto.setPaymentDetails(ride.getPaymentDetails());
        rideDto.setPickupArea(ride.getPickupArea());
        rideDto.setDestinationArea(ride.getDestinationArea());

        return rideDto;
    }
}
