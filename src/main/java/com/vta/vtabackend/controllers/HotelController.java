package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody CreateHotelRequest request, @RequestHeader("Authorization") String token) {
        String result = hotelService.createHotel(request, token);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> getHotels() {
        List<Hotel> hotels = hotelService.getHotels();
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateHotel(@Valid @RequestBody CreateHotelRequest request, @RequestHeader("Authorization") String token) {
        String response = hotelService.updateHotel(request, token);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHotel(@RequestParam("email") String email, @RequestHeader("Authorization") String token) {
        String response = hotelService.deleteHotel(email, token);
        return ResponseEntity.ok(response);
    }
}
