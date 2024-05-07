package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.BookingType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking")
@Data
@Builder
public class Booking {
    @Id
    private final String bookingId;
    private String userEmail;
    private String bookingStartDate;
    private String bookingEndDate;
    private String bookingPrice;
    private BookingType bookingType;
    private String serviceProviderEmail;
}
