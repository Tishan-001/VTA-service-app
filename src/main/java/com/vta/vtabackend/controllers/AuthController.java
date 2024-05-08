package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.*;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/email")
    public ResponseEntity<?> registerWithEmail(@RequestBody @Valid RegisterWithEmailRequest request) {
        try {
            String result = authService.registerWithEmail(request);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register/email/verify")
    private ResponseEntity<?> verifyEmail(@RequestBody @Valid VerifyOTPRequest request) {
        try {
            AuthResponse response = authService.verifyEmailOtp(request.source(), request.otp());
            return ResponseEntity.ok(Map.of("message", response));
        } catch(CustomException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/getcode")
    private ResponseEntity<?> getVerifyCode(@RequestBody @Valid OTPRequest request) {
        try {
            String result = authService.getVerifyCode(request);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login/email")
    private ResponseEntity<?> loginWithEmail(@RequestBody @Valid LoginWithEmailRequest request) {
        try {
            AuthResponse result = authService.loginWithEmail(request);
            return ResponseEntity.ok(Map.of("token", result.token()));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login/admin")
    private ResponseEntity<?> adminlogin(@RequestBody @Valid LoginWithEmailRequest request) {
        try {
            AuthResponse result = authService.adminlogin(request);
            return ResponseEntity.ok(Map.of("token", result.token()));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
