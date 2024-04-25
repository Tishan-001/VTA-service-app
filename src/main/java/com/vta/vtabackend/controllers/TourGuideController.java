package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.services.TourGuideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tourguide")
@RequiredArgsConstructor
public class TourGuideController {
    private final TourGuideService tourGuideService;

    @PostMapping("/register")
    public ResponseEntity<String> saveTourGuide(@RequestBody @Valid RegisterTourGuideRequest request) {
            String result = tourGuideService.saveTourGuide(request);
            return ResponseEntity.ok(result);
    }
}
