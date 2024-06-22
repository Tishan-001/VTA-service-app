package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.TourGuideBookingRequest;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.services.TourGuidBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("tour-guide-booking")
public class TourGuideBookingController {
    private final TourGuidBookingService tourGuidBookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createTourGuideBooking(@RequestBody TourGuideBookingRequest booking, @RequestHeader("Authorization") String token) {
        String result = tourGuidBookingService.createBooking(booking,token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tourGuidBookingService.getBookingsByEmail(token.substring(7)));
    }
}