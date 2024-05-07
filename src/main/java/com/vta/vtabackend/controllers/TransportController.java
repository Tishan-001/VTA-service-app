package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.services.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transport")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    @PostMapping("/register")
    public ResponseEntity<String> saveTransportDetails(@RequestBody @Valid RegisterTransportRequest request) {
        String result = transportService.saveTransportationDetails(request);
        return ResponseEntity.ok(result);
    }
}

