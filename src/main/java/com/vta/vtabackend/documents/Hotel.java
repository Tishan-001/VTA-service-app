package com.vta.vtabackend.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "hotels")
@Data
@Builder
public class Hotel {
    @Id
    private final String id;
    private String name;
    private String description;
    private String photo;
    private String address;
    private String city;
    private String hotline;
    private String mobile;
    private String email;
    private String whatsapp;
    private List<Room> rooms;
    private Double pricePerNight;
    private Integer starRating;
    private List<String> media;
    private String userId;

    // Inner class for Room
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Room {
        private String id;
        private String name;
        private String type;
        private String photo;
        private String price;
        private List<String> facilities;
        private int bedCount;
        private Boolean isAvailable = true;

        public Room(String name,String type, String photo, String price, List<String> facilities, int bedCount) {
            this.id = UUID.randomUUID().toString(); // Generate unique ID for the room
            this.name = name;
            this.type = type;
            this.photo = photo;
            this.price = price;
            this.facilities = facilities;
            this.bedCount = bedCount;
            this.isAvailable = true;
        }
    }
}
