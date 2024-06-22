package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

public record TourGuideBookingRequest(
        @NotNull String tourGuideId,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String phoneNumber,
        @NotNull String startDate,
        @NotNull String startTime,
        @NotNull String endDate,
        @NotNull String endTime,
        @NotNull String specialRequest,
        @NotNull String bookingPrice
) {
}
