package com.example.Cab.Booking.service;

import com.example.Cab.Booking.domain.RideStatus;
import com.example.Cab.Booking.exception.DriverException;
import com.example.Cab.Booking.exception.RideException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.User;
import com.example.Cab.Booking.repository.DriverRepository;
import com.example.Cab.Booking.repository.RideRepository;
import com.example.Cab.Booking.request.RideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final DriverService driverService;
    private final RideRepository rideRepository;
    private final Calculators calculators;
    private final DriverRepository driverRepository;


    @Override
    public Ride requestRide(RideRequest rideRequest, User user) throws Exception {
        double pickupLatitude = rideRequest.getPickupLatitude();
        double pickupLongitude = rideRequest.getPickupLongitude();
        double destLatitude = rideRequest.getDestinationLatitude();
        double destLongitude = rideRequest.getDestinationLongitude();
        String pickupArea = rideRequest.getPickupArea();
        String destinationArea = rideRequest.getDestinationArea();

        Ride exisitingRide = new Ride();

        List<Driver> availableDrivers = driverService.getAvailableDrivers(pickupLatitude,pickupLongitude,exisitingRide);

        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, pickupLatitude,pickupLongitude);

        if(nearestDriver==null){
            throw new DriverException("Driver not available");
        }

        System.out.println("duration ---- before ride");

        Ride ride = createdRideRequest(user, nearestDriver,
                pickupLatitude,pickupLongitude,
                destLatitude,destLongitude,
                pickupArea,destinationArea);

        return ride;
    }

    @Override
    public Ride createdRideRequest(User user, Driver nearestDriver, double pickupLatitude, double pickupLongitude, double destLatitude, double destLongitude, String pickupArea, String destArea) {

        Ride ride = new Ride();

        ride.setDriver(nearestDriver);
        ride.setUser(user);
        ride.setPickupLatitude(pickupLatitude);
        ride.setPickupLongitude(pickupLongitude);
        ride.setDestinationLatitude(destLatitude);
        ride.setDestinationLongitude(destLongitude);
        ride.setStatus(RideStatus.REQUESTED);
        ride.setPickupArea(pickupArea);
        ride.setDestinationArea(destArea);
        return ride;
    }

    @Override
    public void acceptRide(Integer rideId) throws RideException {

        Ride ride = findByRide(rideId);

        ride.setStatus(RideStatus.ACCEPTED);

        Driver driver = ride.getDriver();

        driver.setCurrentRide(ride);

        Random random = new Random();

        int otp = random.nextInt(9000)+1000;
        ride.setOtp(otp);

        driverRepository.save(driver);

        rideRepository.save(ride);
    }

    @Override
    public void declineRide(Integer rideId,Integer driverId) throws RideException {

        Ride ride = findByRide(rideId);

        ride.getDeclinedDrivers().add(driverId);

        System.out.println(ride.getId() + "-" + ride.getDeclinedDrivers());

        List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(), ride.getPickupLongitude(),ride);

        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, ride.getPickupLatitude(),
                ride.getPickupLongitude());

        ride.setDriver(nearestDriver);

        rideRepository.save(ride);
    }

    @Override
    public void startRide(Integer rideId,int otp) throws RideException {

        Ride ride = findByRide(rideId);

        if(otp!=ride.getOtp()){
            throw new RideException("Please provide a valid otp");
        }

        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    @Override
    public void completeRide(Integer rideId) throws RideException {
        Ride ride = findByRide(rideId);

        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());

        double distance = calculators.calculateDistance(ride.getDestinationLatitude(), ride.getDestinationLongitude(), ride.getDestinationLatitude(), ride.getDestinationLongitude());

        LocalDateTime start = ride.getStartTime();
        LocalDateTime end = ride.getEndTime();
        Duration duration = Duration.between(start, end);
        long milliSecond = duration.toMillis();

        System.out.println("duration ------------" + milliSecond);
        double fare = calculators.calculateFare(distance);

        ride.setDistance(Math.round(distance*100.0)/100.0);
        ride.setFare((int)Math.round(fare));
        ride.setDuration(milliSecond);
        ride.setEndTime(LocalDateTime.now());

        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);

        Integer driverRevenue = (int)(driver.getTotalRevenue()+Math.round(fare*0.8));
        driver.setTotalRevenue(driverRevenue);

        driverRepository.save(driver);
        rideRepository.save(ride);

    }

    @Override
    public void cancelRide(Integer rideId) throws RideException {
        Ride ride = findByRide(rideId);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }

    @Override
    public Ride findByRide(Integer rideId) throws RideException {

        Optional<Ride> ride = rideRepository.findById(rideId);

        if(ride.isPresent()){
            return ride.get();
        }
        throw new RideException("ride does not exist " + rideId);
    }
}
