package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.services.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transport")
@RequiredArgsConstructor

public class TransportController {
    private TransportService transport;

    @PostMapping("/register")
    public ResponseEntity<String> saveTransportDetails(@RequestBody @Valid RegisterTransportRequest request) {
        String result = transport.saveTransportationDetails(request);
        return ResponseEntity.ok(result);
    }
}
