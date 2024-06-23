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
        private String serviceProviderId;
        private String vehicleId;
        private String userId;
        private String userName;
        private String userEmail;
        private String contactNo;
        private String bookingStartDate;
        private String bookingEndDate;
        private String bookingPrice;
        private Boolean withDriver;
        private String pickUpLocation = "Not Relevant";
        private String dropOffLocation = "Not Relevant";
        private BookingType bookingType = BookingType.Transport;
        

}

