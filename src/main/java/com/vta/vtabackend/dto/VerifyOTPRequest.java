package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

public record VerifyOTPRequest(@NotNull String source, @NotNull String otp) {
}
