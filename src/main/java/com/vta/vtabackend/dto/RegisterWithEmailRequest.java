package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterWithEmailRequest extends RegistrationRequest {
    private final @NotNull String email;

    public RegisterWithEmailRequest(String name, String email, String password, Role role) {
        super(name, password, role);
        this.email = email;
    }
}
