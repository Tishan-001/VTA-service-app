package com.vta.vtabackend.controllers;

import com.vta.vtabackend.dto.*;
import com.vta.vtabackend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/email")
    public ResponseEntity<?> registerWithEmail(@RequestBody @Valid RegisterWithEmailRequest request) {
        String result = authService.registerWithEmail(request);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/register/email/verify")
    private ResponseEntity<?> verifyEmail(@RequestBody @Valid VerifyOTPRequest request) {
        AuthResponse result = authService.verifyEmailOtp(request.source(), request.otp());
        return ResponseEntity.ok(Map.of("token", result.token(), "role", result.role()));
    }

    @PostMapping("/getcode")
    private ResponseEntity<?> getVerifyCode(@RequestBody @Valid OTPRequest request) {
        String result = authService.getVerifyCode(request);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login/email")
    private ResponseEntity<?> loginWithEmail(@RequestBody @Valid LoginWithEmailRequest request) {
        AuthResponse result = authService.loginWithEmail(request);
        return ResponseEntity.ok(Map.of("token", result.token(), "role", result.role()));
    }

    @PostMapping("/login/admin")
    private ResponseEntity<?> adminlogin(@RequestBody @Valid LoginWithEmailRequest request) {
        AuthResponse result = authService.adminlogin(request);
        return ResponseEntity.ok(Map.of("token", result.token()));
    }

    @GetMapping("/count")
    private ResponseEntity<String> count() {
        String count = authService.getCount();
        return ResponseEntity.ok(count);
    }
}
