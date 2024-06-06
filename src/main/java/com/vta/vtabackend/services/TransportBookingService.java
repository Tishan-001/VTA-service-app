package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.TransportBooking;
import com.vta.vtabackend.documents.UserDetails;
import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TransportBookingRepository;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportBookingService {
    private final TransportRepository transportRepository;
    private final TransportBookingRepository transportBookingRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    public String createBooking(TransportBookingRequest request, String token){

        if (!jwtUtil.validateToken(token.substring(7))) {
            throw new CustomException("Invalid or expired token");
        }
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));

        Transport service = transportRepository.getTransportationByEmail(request.serviceProviderEmail());
        UserDetails user = userRepository.findById(userId).orElseThrow(()->
        new CustomException("Authentication failed!"));
        try {
            TransportBooking transportBooking = buildTransportBooking(request,userId);
            transportBookingRepository.save(transportBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new CustomException("Failed to create booking: "+e.getMessage());
        }
    }
    public List<TransportBooking> getBookingsByEmail(EmailRequest request){
        return transportBookingRepository.getByServiceProviderEmail(request.getEmail())
                .orElseThrow(()-> new CustomException("No booking available for "+ request.getEmail().toString()));
    }

    private TransportBooking buildTransportBooking(TransportBookingRequest request, String userId) {
        return TransportBooking.builder()
                .bookingId(generateBookingId())
                .userId(userId)
                .pickUpLocation(request.pickUpLocation())
                .dropOffLocation(request.dropOffLocation())
                .bookingStartDate(request.bookingStartDate())
                .bookingEndDate(request.bookingEndDate())
                .bookingPrice(request.bookingPrice())
                .serviceProviderEmail(request.serviceProviderEmail())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}
