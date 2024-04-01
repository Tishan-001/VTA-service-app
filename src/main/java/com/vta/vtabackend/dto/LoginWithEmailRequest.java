package com.vta.vtabackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginWithEmailRequest(@NotNull @Email String email, @NotNull String password) {
}
