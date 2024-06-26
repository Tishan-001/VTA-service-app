package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.services.TourGuideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tourguides")
@RequiredArgsConstructor
public class TourGuideController {
    private final TourGuideService tourGuideService;

    @PostMapping("/register")
    public ResponseEntity<String> saveTourGuide(@RequestBody @Valid RegisterTourGuideRequest request, @RequestHeader("Authorization") String token) {
        String result = tourGuideService.saveTourGuide(request, token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getTourGuides() {
        List<TourGuide> tourGuides = tourGuideService.getTourGuides();
        return ResponseEntity.ok(tourGuides);
    }

    @GetMapping("/tourguide/{id}")
    public ResponseEntity<?> getTourGuide(@PathVariable String id) {
        TourGuide tourGuide = tourGuideService.getTourguide(id);
        return ResponseEntity.ok(tourGuide);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTourGuide(@RequestBody RegisterTourGuideRequest request, @RequestHeader("Authorization") String token) {
        String result = tourGuideService.updateTourGuide(request, token.substring(7));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTourGuide(@RequestParam("email") String email, @RequestHeader("Authorization") String token) {
        String result = tourGuideService.deleteTourGuide(email, token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countTourGuide() {
        String response = tourGuideService.getTourGuidesCount();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/guider")
    public ResponseEntity<?> guiderTourGuide(@RequestHeader("Authorization") String token) {
        TourGuide tourGuide = tourGuideService.getTourguideByEmail(token.substring(7));
        return ResponseEntity.ok(tourGuide);
    }
}
