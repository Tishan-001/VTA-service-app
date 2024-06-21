package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

public record HotelBookingRequest(
        @NotNull String roomId,
        @NotNull String arrivalDate,
        @NotNull String departureDate,
        @NotNull String userFirstName,
        @NotNull String userLastName,
        @NotNull String contactEmail,
        @NotNull String contactTelephone,
        @NotNull String noOfBeds,
        @NotNull String specialRequest,
        @NotNull String bookingPrice
) {}
