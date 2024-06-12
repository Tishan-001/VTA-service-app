package com.vta.vtabackend.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tourpackage")
@Data
@Builder
public class TourPackage {
    @Id
    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String description;
    private String duration;
    private String ratting;
    private String image;
    private String price;
    private List<TimePlane> timePlaneList;
    private List<String> gallery;

    // Inner class for TimePlane
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class TimePlane{
        private String id;
        private String date;
        private String place;
        private String reservation;
        private String description;
    }
}
