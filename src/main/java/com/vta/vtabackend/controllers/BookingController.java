package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Booking;
import com.vta.vtabackend.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

}
