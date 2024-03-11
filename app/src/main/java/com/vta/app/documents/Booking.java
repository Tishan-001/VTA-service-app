package com.vta.app.documents;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Getter
@Setter
@Document(collection = "bookings")
public class Booking {
    @Id
    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer adultCount;
    private Integer childCount;
    private Date checkIn;
    private Date checkOut;
    private String userId;
    private Double totalCost;

}
