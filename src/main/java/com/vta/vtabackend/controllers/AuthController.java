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
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid VerifyOTPRequest request) {
        AuthResponse result = authService.verifyEmailOtp(request.source(), request.otp());
        return ResponseEntity.ok(Map.of("token", result.token(), "role", result.role()));
    }

    @PostMapping("/getcode")
    public ResponseEntity<?> getVerifyCode(@RequestBody @Valid OTPRequest request) {
        String result = authService.getVerifyCode(request);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login/email")
    public ResponseEntity<?> loginWithEmail(@RequestBody @Valid LoginWithEmailRequest request) {
        AuthResponse result = authService.loginWithEmail(request);
        return ResponseEntity.ok(Map.of("token", result.token(), "role", result.role()));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> adminlogin(@RequestBody @Valid LoginWithEmailRequest request) {
        AuthResponse result = authService.adminlogin(request);
        return ResponseEntity.ok(Map.of("token", result.token()));
    }

    @GetMapping("/count")
    public ResponseEntity<String> count() {
        String count = authService.getCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotEmail email) {
        String result = authService.forgotPassword(email);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/forgot/reset/password")
    public ResponseEntity<?> restPassword(@RequestBody String password, @RequestHeader("Authorization") String token) {
        String response = authService.resetPassword(password, token.substring(7));
        return ResponseEntity.ok(Map.of("message", response));
    }
}
