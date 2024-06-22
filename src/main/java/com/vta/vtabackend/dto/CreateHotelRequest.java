package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateHotelRequest(@NotNull String name,
                                 @NotNull String type,
                                 @NotNull String category,
                                 @NotNull String address,
                                 @NotNull String city,
                                 @NotNull String hotline,
                                 @NotNull String mobileNo,
                                 @NotNull String email,
                                 @NotNull String whatsapp,
                                 @NotNull String description,
                                 @NotNull List<String> media,
                                 @NotNull Double pricePerNight) {
}
