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
@Document(collection = "tourGuideBooking")
public class TourGuideBooking {
    @Id
    private String bookingId;
    private String userId;
    private String userContact;
    private String Location;
    private String bookingStartDate;
    private String bookingEndDate;
    private String bookingPrice;
    private final BookingType bookingType =BookingType.TourGuide;
    private String serviceProviderId;

}
