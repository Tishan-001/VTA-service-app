package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tour_guide")
public class TourGuide {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String address;
    private Role role;
    private String media;
    private String price;
    private Integer starRating;
    private String description;
    private String userId;
}
