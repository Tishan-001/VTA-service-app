package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Booking;
import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.exceptions.ApiException;
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

        try {
            String result = transportBookingService.createBooking(booking,token);
            return ResponseEntity.ok(result);
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestBody EmailRequest request){
        try {
            return ResponseEntity.ok(transportBookingService.getBookingsByEmail(request));
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
