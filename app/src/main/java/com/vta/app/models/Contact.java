package com.vta.app.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String mobileNumber;
    private String homeNumber;
    private String address;
    private String email;
}
