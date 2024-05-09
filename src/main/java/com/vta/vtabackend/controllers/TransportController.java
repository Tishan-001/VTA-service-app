package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.dto.AuthResponse;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.RegisterTourGuideRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.response.EmailRequest;
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

    @PostMapping("/register")
    public ResponseEntity<String> saveTransportDetails(@RequestBody @Valid RegisterTransportRequest request) {
        String result = transportService.saveTransportationDetails(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAsTransport(@RequestBody @Valid LoginWithEmailRequest request){
        try{
            AuthResponse authResponse = transportService.loginWithEmail(request);
            return ResponseEntity.ok(authResponse);
        }catch(CustomException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getTransportaions(){
        List<Transport> transports = transportService.getTransports();
        return ResponseEntity.ok(transports);
    }

    @GetMapping("/transport")
    public ResponseEntity<?> getTransportation(@RequestBody EmailRequest emailRequest){
        try {
            Transport transport = transportService.getTransport(emailRequest.getEmail());
            return ResponseEntity.ok(transport);
        }catch( CustomException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/update")
    public ResponseEntity<?> updateTransport(@Valid @RequestBody RegisterTransportRequest request, @RequestHeader("Authorization") String token) {
        String result = transportService.updateTransport(request, token);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTourGuide(@RequestBody EmailRequest emailRequest, @RequestHeader("Authorization") String token) {
        String result = transportService.deleteTransport(emailRequest.getEmail(), token);
        return ResponseEntity.ok(result);
    }
}

