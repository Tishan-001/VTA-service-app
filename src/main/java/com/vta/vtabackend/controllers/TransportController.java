package com.vta.vtabackend.controllers;

import com.cloudinary.http44.api.Response;
import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.dto.CreateVehicleRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.services.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        String result = transportService.saveTransportationDetails(request, token.substring(7));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTransports() {
        List<Transport> transports = transportService.getTransports();
        return ResponseEntity.ok(transports);
    }

    @GetMapping("/transport-id/{id}")
    public ResponseEntity<?> getTransportDetailsById(@PathVariable("id") String id) {
        Transport transport = transportService.getTransport(id);
        return ResponseEntity.ok(transport);
    }
    @GetMapping("/transport")
    public  ResponseEntity<?> getTransportDetails(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        Transport transport = transportService.getTransportByToken(token);
        return ResponseEntity.ok(transport);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countTransport() {
        String response = transportService.getTourGuidesCount();
        return ResponseEntity.ok(response);
    }


}
