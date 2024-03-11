package com.vta.app.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateHotelStep1Request(@NotNull String name, @NotNull String address, @NotNull String district,
                                      @NotNull String country, @NotNull String hotline, @NotNull String mobileNo,
                                      @NotNull String email, @NotNull String whatsapp, @NotNull String description, @NotNull String type,
                                      @NotNull List<String> facilities, @NotNull Double pricePerNight, @NotNull Integer starRating) {
}
