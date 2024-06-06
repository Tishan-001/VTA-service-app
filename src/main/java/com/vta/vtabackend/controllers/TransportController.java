package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.services.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transports")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    @PostMapping("/create")
    public ResponseEntity<String> saveTransportDetails(@RequestBody @Valid RegisterTransportRequest request, @RequestHeader("Authorization") String token) {
        String result = transportService.saveTransportationDetails(request, token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTransports() {
        List<Transport> transports = transportService.getTransports();
        return ResponseEntity.ok(transports);
    }

    @GetMapping("/transport/{id}")
    public ResponseEntity<?> getTransportDetails(@PathVariable("id") String id) {
        Transport transport = transportService.getTransport(id);
        return ResponseEntity.ok(transport);
    }
}
