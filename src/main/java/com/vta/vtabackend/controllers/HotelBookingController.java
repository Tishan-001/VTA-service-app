package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.HotelBookingRequest;
import com.vta.vtabackend.exceptions.ApiException;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.services.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotel-booking")
@RequiredArgsConstructor
public class HotelBookingController {
    private final HotelBookingService hotelBookingService;
    @PostMapping("/create")
    public ResponseEntity<?> createHotelBooking(@RequestBody HotelBookingRequest booking, @RequestHeader("Authorization") String token) {

        try {
            String result = hotelBookingService.createBooking(booking,token);
            return ResponseEntity.ok(result);
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestBody EmailRequest request){
        try {
            return ResponseEntity.ok(hotelBookingService.getBookingsByServiceProviderEmail(request));
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
