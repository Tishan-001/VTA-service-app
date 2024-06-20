package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.dto.CreateRoomRequest;
import com.vta.vtabackend.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody CreateHotelRequest request, @RequestHeader("Authorization") String token) {
        String result = hotelService.createHotel(request, token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getHotels() {
        List<Hotel> hotels = hotelService.getHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getHotel(@RequestHeader("Authorization") String token) {
        Hotel result = hotelService.getHotel(token.substring(7));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateHotel(@Valid @RequestBody CreateHotelRequest request, @RequestHeader("Authorization") String token) {
        String response = hotelService.updateHotel(request, token.substring(7));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String response = hotelService.deleteHotel(id, token.substring(7));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getHotelCount() {
        String count = hotelService.getHotelCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/add/room")
    public ResponseEntity<?> addRoom(@RequestBody CreateRoomRequest request, @RequestHeader("Authorization") String token) {
        String response = hotelService.addRoom(request, token.substring(7));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String response = hotelService.deleteRoom(id, token.substring(7));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/rooms")
    public ResponseEntity<List<Hotel.Room>> getHotelRooms(@RequestHeader("Authorization") String token) {
        List<Hotel.Room> rooms = hotelService.getHotelRooms(token.substring(7));
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/get/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable String id) {
        Hotel.Room room = hotelService.getRoom(id);
        return ResponseEntity.ok(room);
    }
}
