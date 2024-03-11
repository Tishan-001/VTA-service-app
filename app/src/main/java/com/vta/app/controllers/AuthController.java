package com.vta.app.controllers;

import com.vta.app.dto.AuthResponse;
import com.vta.app.dto.GenericResponse;
import com.vta.app.dto.LoginWithEmailRequest;
import com.vta.app.dto.RegisterWithEmailRequest;
import com.vta.app.dto.VerifyOTPRequest;
import com.vta.app.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/email")
    private Mono<GenericResponse> registerWithEmail(@RequestBody @Valid RegisterWithEmailRequest request) {
        return authService.registerWithEmail(request);
    }

    @PostMapping("/register/email/verify")
    private Mono<AuthResponse> verifyEmail(@RequestBody @Valid VerifyOTPRequest request) {
        return authService.verifyEmailOtp(request.source(), request.otp());
    }

    @PostMapping("/login/email")
    public Mono<AuthResponse> loginWithEmail(@RequestBody @Valid LoginWithEmailRequest request) {
        return authService.loginWithEmail(request);
    }

    @PostMapping("/admin")
    public Mono<AuthResponse> loginAdmin(@RequestBody @Valid LoginWithEmailRequest request) {
        return authService.loginAdmin(request);
    }
}
