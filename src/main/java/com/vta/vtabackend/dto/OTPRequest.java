package com.vta.vtabackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record OTPRequest(@NotNull @Email String email) {
}
