package com.vta.vtabackend.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "hotels")
@Data
@Builder
public class Hotel {
    @Id
    private final String id;
    private String logo;
    private String name;
    private String description;
    private String address;
    private String district;
    private String country;
    private String hotline;
    private String mobile;
    private String email;
    private String whatsapp;
    private String type;
    private List<String> facilities;
    private Double pricePerNight;
    private Integer starRating;
    private List<String> media;
    private String userId;
}
