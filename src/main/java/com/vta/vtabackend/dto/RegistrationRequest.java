package com.vta.vtabackend.dto;

import com.vta.vtabackend.enums.Role;
import lombok.Data;

@Data
public class RegistrationRequest {
    private final String name;
    private final String password;
    private final Role role;
}
