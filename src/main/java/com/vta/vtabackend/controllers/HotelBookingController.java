package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.HotelBookingRequest;
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
        String result = hotelBookingService.createBooking(booking,token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestBody EmailRequest request){
        return ResponseEntity.ok(hotelBookingService.getBookingsByServiceProviderEmail(request));
    }
}
