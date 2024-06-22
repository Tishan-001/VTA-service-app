package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.BookingType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tour_guide_booking")
public class TourGuideBooking {
    @Id
    private String bookingId;
    private String userId;
    private String tourGuideId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String specialRequest;
    private String price;
}
