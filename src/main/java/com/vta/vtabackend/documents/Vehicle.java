package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.VehicleCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Vehicle {
    private String id;
    private String transportId;
    private String type;
    private VehicleCategory vehicleCategory;
    private String photo;
    private Double price;
    private List<String> features;
    private Integer ratings;

    public Vehicle(String transportId, String type, VehicleCategory vehicleCategory, String photo, Double price, List<String> features) {
        this.id = UUID.randomUUID().toString();
        this.transportId = transportId;
        this.type = type;
        this.vehicleCategory = vehicleCategory;
        this.photo = photo;
        this.price = price;
        this.features = features;
        this.ratings = 0;
    }
}
