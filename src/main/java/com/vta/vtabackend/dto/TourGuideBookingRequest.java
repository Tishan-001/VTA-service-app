package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

public record TourGuideBookingRequest(
        @NotNull String Location,
        @NotNull String bookingStartDate,
        @NotNull String bookingEndDate,
        @NotNull String bookingPrice,
        @NotNull String userContact,
        @NotNull String serviceProviderId
) {
}
