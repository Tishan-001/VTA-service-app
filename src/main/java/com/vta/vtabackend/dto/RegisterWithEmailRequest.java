package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterWithEmailRequest extends RegistrationRequest {
    private final @NotNull String email;

    public RegisterWithEmailRequest(String name, String email, String password) {
        super(name, password);
        this.email = email;
    }
}
