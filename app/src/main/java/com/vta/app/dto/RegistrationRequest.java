package com.vta.app.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private final String name;
    private final String password;
}
