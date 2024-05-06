package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.VehicleCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterTransportRequest(
        @NotNull String name,
        @NotNull String password,
        @NotNull @Email String email,
        @NotNull String mobile,
        @NotNull String address,
        @NotNull Double price,
        @NotNull String description,
        List<String> features,
        @NotNull VehicleCategory vehicleCategory) {
}