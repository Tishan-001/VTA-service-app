package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.HotelPackage;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document(collection = "hotel_booking")
public class HotelBooking {
    private final String bookingId;
    private String userId;
    private String serviceProviderEmail;
    private String roomId;
    private String arrivalDate;
    private String departureDate;
    private String userFirstName;
    private String userLastName;
    private String contactEmail;
    private String contactTelephone;
    private String noOfBeds;
    private String specialRequest;
    private HotelPackage hotelPackage;
    private String bookingPrice;

}
