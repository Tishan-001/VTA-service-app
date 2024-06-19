package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.BookingType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transport_booking")
public class   TransportBooking {
        @Id
        private String bookingId;
        private String userId;
        private String pickUpLocation;
        private String dropOffLocation;
        private String bookingStartDate;
        private String bookingEndDate;
        private String bookingPrice;
        private final BookingType bookingType = BookingType.Transport;
        private String serviceProviderId;

}

