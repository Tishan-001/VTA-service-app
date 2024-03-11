package com.vta.app.dto;

import jakarta.validation.constraints.NotNull;

public record VerifyOTPRequest(@NotNull String source, @NotNull String otp) {
}
