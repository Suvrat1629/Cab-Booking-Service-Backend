package com.example.Cab.Booking.controller;

import com.example.Cab.Booking.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<MessageResponse> homeController(){
        MessageResponse msg = new MessageResponse("Welcome to cab booking system");
        return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);
    }
}
