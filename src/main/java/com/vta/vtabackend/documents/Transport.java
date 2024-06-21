package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.enums.VehicleCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

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
    private String imageUrl;
    private Double ratings;
    private Integer reviews;
    private String description;
    private List<Vehicle> vehicles;
    private Role role = Role.TRANSPORT;
    private boolean verified;

    public void removeVehicleById(String vehicleId) {
        if (vehicles != null) {
            vehicles.removeIf(vehicle -> vehicle.getId().toString().equals(vehicleId));
        }
    }

    // Inner class for Vehicle
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Vehicle {
        private String id;
        private String type;
        private VehicleCategory vehicleCategory;
        private String photo;
        private Double price;
        private List<String> features;
        private Integer ratings;

        public Vehicle(String type, VehicleCategory vehicleCategory, String photo, Double price, List<String> features) {
            this.id = UUID.randomUUID().toString();
            this.type = type;
            this.vehicleCategory = vehicleCategory;
            this.photo = photo;
            this.price = price;
            this.features = features;
            this.ratings = 0;

        }
    }

}

