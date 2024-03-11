package com.vta.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    private boolean success;

    public static GenericResponse SUCCESS() {
        return new GenericResponse(true);
    }

    public static GenericResponse FAILED() {
        return new GenericResponse(false);
    }
}
