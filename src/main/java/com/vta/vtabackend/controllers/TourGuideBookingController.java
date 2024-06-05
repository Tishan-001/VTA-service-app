package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.TourGuideBookingRequest;
import com.vta.vtabackend.exceptions.ApiException;
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

        try {
            String result = tourGuidBookingService.createBooking(booking,token);
            return ResponseEntity.ok(result);
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestBody EmailRequest request){
        try {
            return ResponseEntity.ok(tourGuidBookingService.getBookingsByEmail(request));
        }catch (ApiException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
