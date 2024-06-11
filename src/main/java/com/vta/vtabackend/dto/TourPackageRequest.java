package com.vta.vtabackend.dto;

import com.vta.vtabackend.documents.TourPackage;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TourPackageRequest(
        @NotNull String name,
        @NotNull String startDate,
        @NotNull String endDate,
        @NotNull String description,
        @NotNull String duration,
        @NotNull String ratting,
        @NotNull String image,
        @NotNull String price,
        @NotNull List<TourPackage.TimePlane> timePlaneList,
        @NotNull List<String> gallery
) {
}
