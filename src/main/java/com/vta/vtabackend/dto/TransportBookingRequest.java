package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.BookingType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public record TransportBookingRequest(
                                      @NotNull String pickUpLocation,
                                      @NotNull String dropOffLocation,
                                      @NotNull String bookingStartDate,
                                      @NotNull String bookingEndDate,
                                      @NotNull String bookingPrice,
                                      @NotNull BookingType bookingType,
                                      @NotNull String serviceProviderEmail
                                      ) {
}
