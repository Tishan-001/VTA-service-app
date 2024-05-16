package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.TourPackage;
import com.vta.vtabackend.dto.TourPackageRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.services.TourPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tourpackage")
@RequiredArgsConstructor
public class TourPackageController {
    private final TourPackageService tourPackageService;

    @PostMapping("/create")
    public ResponseEntity<?> addTourPackage(@RequestBody @Valid TourPackageRequest tourPackage, @RequestHeader("Authorization") String token) {
        try {
            String response = tourPackageService.createPackage(tourPackage, token);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTourPackages() {
        List<TourPackage> tourPackages = tourPackageService.getAll();
        return ResponseEntity.ok(tourPackages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourPackageById(@PathVariable("id") String id) {
        try {
            TourPackage tourPackage = tourPackageService.getTourPackage(id);
            return ResponseEntity.ok(tourPackage);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
