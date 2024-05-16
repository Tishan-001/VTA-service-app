package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.enums.VehicleCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transport")
public class Transport {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String address;
    private Double price;
    private List<String> features;
    private Double ratings;
    private Integer reviews;
    private String description;
    private VehicleCategory vehicleCategory;
    private String userId;
    private final Role role = Role.TRANSPORT;
    private boolean verified;

}

