package com.vta.vtabackend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRoomRequest(@NotNull String name,
                                @NotNull String type,
                                @NotNull String photo,
                                @NotNull String price,
                                @NotNull List<String> facilities,
                                @NotNull int bedCount) {
}
