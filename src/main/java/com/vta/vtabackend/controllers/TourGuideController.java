package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.exceptions.CustomException;
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
        String result = tourGuideService.saveTourGuide(request, token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getTourGuides() {
        List<TourGuide> tourGuides = tourGuideService.getTourGuides();
        return ResponseEntity.ok(tourGuides);
    }

    @GetMapping("/tourguide/{id}")
    public ResponseEntity<?> getTourGuide(@PathVariable String id) {
        try {
            TourGuide tourGuide = tourGuideService.getTourguide(id);
            return ResponseEntity.ok(tourGuide);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/update")
    public ResponseEntity<?> updateTourGuide(@Valid @RequestBody RegisterTourGuideRequest request, @RequestHeader("Authorization") String token) {
        String result = tourGuideService.updateTourGuide(request, token);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTourGuide(@RequestParam("email") String email, @RequestHeader("Authorization") String token) {
        String result = tourGuideService.deleteTourGuide(email, token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countTourGuide() {
        String response = tourGuideService.getTourGuidesCount();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/guider/{email}")
    public ResponseEntity<?> guiderTourGuide(@PathVariable String email) {
        try {
            TourGuide tourGuide = tourGuideService.getTourguideByEmail(email);
            return ResponseEntity.ok(tourGuide);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}