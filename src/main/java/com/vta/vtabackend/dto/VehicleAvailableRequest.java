package com.vta.vtabackend.dto;

public record VehicleAvailableRequest(
        String location,
        String bookingStartDate,
        String bookingEndDate,
        String category
) {
}
