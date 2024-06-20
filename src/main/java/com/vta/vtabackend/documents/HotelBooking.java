package com.vta.vtabackend.documents;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document(collection = "hotel_booking")
public class HotelBooking {
    private String bookingId;
    private String userId;
    private String hotelId;
    private String roomId;
    private String roomName;
    private String arrivalDate;
    private String departureDate;
    private String userFirstName;
    private String userLastName;
    private String contactEmail;
    private String contactTelephone;
    private String noOfBeds;
    private String specialRequest;
    private String bookingPrice;

}
