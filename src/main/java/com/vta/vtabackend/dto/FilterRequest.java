package com.vta.vtabackend.dto;

public record FilterRequest(
        String city,
        String checkInDate,
        String checkOutDate
) {
}
