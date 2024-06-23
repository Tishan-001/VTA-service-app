package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.BookingType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public record TransportBookingRequest(
                                      @NotNull String userName,
                                      @NotNull String contactNo,
                                      @NotNull String bookingStartDate,
                                      @NotNull String bookingEndDate,
                                      @NotNull String bookingPrice,
                                      @NotNull String serviceProviderId,
                                      @NotNull String vehicleID,
                                      @NotNull Boolean withDriver,
                                      @NotNull String pickUpLocation,
                                      @NotNull String dropOffLocation
                                      ) {
}
