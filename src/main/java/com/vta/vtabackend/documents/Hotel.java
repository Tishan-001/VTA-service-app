package com.vta.vtabackend.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private String type;
    private List<String> facilities;
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
        private String type;
        private String photo;
        private String price;
        private int bedCount;
    }
}
