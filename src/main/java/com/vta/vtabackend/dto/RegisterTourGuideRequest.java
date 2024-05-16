package com.vta.vtabackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterTourGuideRequest(@NotNull String name,
                                       @NotNull @Email String email,
                                       @NotNull String mobile,
                                       @NotNull String password,
                                       @NotNull String address,
                                       @NotNull String price,
                                       String media,
                                       @NotNull Integer starRating,
                                       @NotNull String description) {
}
