package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Vehicle;
import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.services.TransportBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transport-booking")
@RequiredArgsConstructor
public class TransportBookingController {
    private final TransportBookingService transportBookingService;
    @PostMapping("/create")
    public ResponseEntity<?> createTransportBooking(@RequestBody TransportBookingRequest booking, @RequestHeader ("Authorization") String token) {
        String result = transportBookingService.createBooking(booking,token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-bookings-service")
    public ResponseEntity<?> getBookingsByService(@RequestHeader ("Authorization") String token){
        System.out.println("Token is :"+token);
        if(token != null && !token.isEmpty()){
            token = token.substring(7);
            return ResponseEntity.ok(transportBookingService.getBookingsService(token));

        }else{
            return ResponseEntity.badRequest().body("Invalid or missing token");
        }

    }
    @GetMapping("/get-bookings-user")
    public ResponseEntity<?> getBookingsByUser(@RequestHeader ("Authorization") String token){
        if(token != null && !token.isEmpty()){
            token = token.substring(7);
            return ResponseEntity.ok(transportBookingService.getBookingsByUser(token));

        }else{
            return ResponseEntity.badRequest().body("Invalid or missing token");
        }

    }
}