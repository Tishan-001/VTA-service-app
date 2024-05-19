package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.BookingType;
import jakarta.validation.constraints.NotNull;

public record TourGuidBookingRequest(
        @NotNull String Location,
        @NotNull String bookingStartDate,
        @NotNull String bookingEndDate,
        @NotNull String bookingPrice,
        @NotNull String userContact,
        @NotNull String serviceProviderId
) {

}
