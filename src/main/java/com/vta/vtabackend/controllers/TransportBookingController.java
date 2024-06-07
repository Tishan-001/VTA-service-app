package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.services.TransportBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transport-booking")
@RequiredArgsConstructor
public class TransportBookingController {
    private final TransportBookingService transportBookingService;
    @PostMapping("/create")
    public ResponseEntity<?> createTransportBooking(@RequestBody TransportBookingRequest booking, @RequestHeader ("Authorization") String token) {
        String result = transportBookingService.createBooking(booking,token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestBody EmailRequest request){
        return ResponseEntity.ok(transportBookingService.getBookingsByEmail(request));
    }
}
