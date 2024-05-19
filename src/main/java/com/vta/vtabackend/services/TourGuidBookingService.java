package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.TourGuidBookingRequest;
import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.*;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor

public class TourGuidBookingService {
    private final TourGuidBookingRepository tourGuidBookingRepository;
    private final TourGuideRepository tourGuideRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    public String createBooking(TourGuidBookingRequest request, String token){

        if (!jwtUtil.validateToken(token.substring(7))) {
            throw new CustomException("Invalid or expired token");
        }
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));

        TourGuide service = tourGuideRepository.getTourguideByEmail(request.serviceProviderId());
        if(service==null){
            throw new CustomException("Tour Guide not found!");
        }
        UserDetails user = userRepository.findById(userId).orElseThrow(()->
                new CustomException("Authentication failed!"));
        try {
            TourGuideBooking tourGuideBooking = buildTourGuideBooking(request,userId);
            tourGuidBookingRepository.save(tourGuideBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new CustomException("Failed to create booking: "+e.getMessage());
        }
    }
    public List<TourGuideBooking> getBookingsByEmail(EmailRequest request){
        return tourGuidBookingRepository.getByServiceProviderEmail(request.getEmail())
                .orElseThrow(()-> new CustomException("No booking available for "+ request.getEmail().toString()));
    }

    private TourGuideBooking buildTourGuideBooking(TourGuidBookingRequest request, String userId) {
        return TourGuideBooking.builder()
                .bookingId(generateBookingId())
                .userId(userId)
                .Location(request.Location())
                .bookingStartDate(request.bookingStartDate())
                .bookingEndDate(request.bookingEndDate())
                .bookingPrice(request.bookingPrice())
                .userContact(request.userContact())
                .serviceProviderId(request.serviceProviderId())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}

