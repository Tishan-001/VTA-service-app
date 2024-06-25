package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

public record ForgotEmail(@NotNull String email) {
}
