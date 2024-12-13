package com.example.Cab.Booking.service;

import com.example.Cab.Booking.Config.JwtProvider;
import com.example.Cab.Booking.domain.RideStatus;
import com.example.Cab.Booking.domain.UserRole;
import com.example.Cab.Booking.exception.DriverException;
import com.example.Cab.Booking.model.Driver;
import com.example.Cab.Booking.model.License;
import com.example.Cab.Booking.model.Ride;
import com.example.Cab.Booking.model.Vehicle;
import com.example.Cab.Booking.repository.DriverRepository;
import com.example.Cab.Booking.repository.LicenseRepository;
import com.example.Cab.Booking.repository.RideRepository;
import com.example.Cab.Booking.repository.VehicleRepository;
import com.example.Cab.Booking.request.DriverSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService{

    private final DriverRepository driverRepository;
    private Calculators distanceCalculator;
    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;
    private VehicleRepository vehicleRespository;
    private LicenseRepository licenseRepository;

    private RideRepository rideRepository;

    @Override
    public Driver registerDriver(DriverSignupRequest driverSignupRequest) {

        License license = driverSignupRequest.getLicense();
        Vehicle vehicle = driverSignupRequest.getVehicle();

        License createdLicense = new License();

        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseExpiration(license.getLicenseExpiration());
        createdLicense.setId(license.getId());

        License savedLicense = licenseRepository.save(createdLicense);

        Vehicle createdVehicle = new Vehicle();
        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setId(vehicle.getId());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModel(vehicle.getModel());
        createdVehicle.setYear(vehicle.getYear());

        Vehicle savedVehicle = vehicleRespository.save(createdVehicle);

        Driver driver = new Driver();

        String encodedPassword = passwordEncoder.encode(driverSignupRequest.getPassword());

        driver.setEmail(driverSignupRequest.getEmail());
        driver.setName(driverSignupRequest.getName());
        driver.setMobile(driverSignupRequest.getMobile());
        driver.setPassword(driverSignupRequest.getPassword());
        driver.setLicense(savedLicense);
        driver.setVehicle(savedVehicle);
        driver.setRole(UserRole.DRIVER);

        driver.setLatitude(driverSignupRequest.getLatitude());
        driver.setLongitude(driverSignupRequest.getLongitude());

        Driver createdDriver = driverRepository.save(driver);

        savedLicense.setDriver(createdDriver);
        savedVehicle.setDriver(createdDriver);

        licenseRepository.save(savedLicense);
        vehicleRespository.save(savedVehicle);

        return createdDriver;
    }

    @Override
    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride) {

        List<Driver> allDrivers = driverRepository.findAll();

        List<Driver> availableDrivers = new ArrayList<>();

        for(Driver driver: allDrivers){

            if(driver.getCurrentRide()!=null && driver.getCurrentRide().getStatus()!= RideStatus.COMPLETED){
                continue;
            }

            if (ride.getDeclinedDrivers().contains(driver.getId())){
                continue;
            }

            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = distanceCalculator.calculateDistance(driverLatitude, driverLongitude, pickupLatitude, pickupLongitude);

            availableDrivers.add(driver);

        }

        return availableDrivers;
    }

    @Override
    public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {
        double min = Double.MAX_VALUE;
        Driver nearestDriver = null;

        for(Driver driver:availableDrivers){
            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = distanceCalculator.calculateDistance(pickupLatitude,pickupLongitude,driverLatitude,driverLongitude);

            if(min>distance){
                min = distance;
                nearestDriver = driver;
            }
        }
        return nearestDriver;
    }

    @Override
    public Driver getReqDriverProfile(String jwt) throws DriverException {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Driver driver = driverRepository.findByEmail(email);

        if(driver==null){
            throw new DriverException("driver does not exist");
        }

        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Integer driverId) throws DriverException {
        Driver driver  = findDriverId(driverId);
        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRide(Integer driverId) throws DriverException {

        List<Ride> allocatedRides = driverRepository.getAllocatedRides(driverId);
        return allocatedRides;
    }

    public Driver findDriverId(@RequestParam Integer driverId) throws DriverException {

        Optional<Driver> opt = driverRepository.findById(driverId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new DriverException("Driver does not exist with id " + driverId);
    }

    @Override
    public List<Ride> completedRide(Integer driverId) throws DriverException {
        List<Ride> rides = driverRepository.getCompletedRides(driverId);
        return rides;
    }

}
