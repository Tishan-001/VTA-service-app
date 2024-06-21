package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.VehicleCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateVehicleRequest(
        String id,
        @NotNull String type,
        @NotNull VehicleCategory vehicleCategory,
        @NotNull String photo,
        @NotNull Double price,
        @NotNull List<String> features
        ) {
}
