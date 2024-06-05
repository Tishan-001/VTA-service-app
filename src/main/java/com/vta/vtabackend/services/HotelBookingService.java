package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.HotelBookingRequest;
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
public class HotelBookingService {
    private final HotelRepository hotelRepository;
    private final HotelBookingRepository hotelBookingRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    public String createBooking(HotelBookingRequest request, String token){

        if (!jwtUtil.validateToken(token.substring(7))) {
            throw new CustomException("Invalid or expired token");
        }
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));

        Hotel service = hotelRepository.getHotelByEmail(request.serviceProviderEmail());
        if(service!=null){
              throw new CustomException("Hotel Service Not found!");
        }
        UserDetails user = userRepository.findById(userId).orElseThrow(()->
                new CustomException("Authentication failed!"));
        try {
            HotelBooking hotelBooking = buildHotelBooking(request,userId);
            hotelBookingRepository.save(hotelBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new CustomException("Failed to create booking: "+e.getMessage());
        }
    }
    public List<HotelBooking> getBookingsByServiceProviderEmail(EmailRequest request){
        return hotelBookingRepository.getByServiceProviderEmail(request.getEmail())
                .orElseThrow(()-> new CustomException("No booking available for "+ request.getEmail().toString()));
    }

    private HotelBooking buildHotelBooking(HotelBookingRequest request, String userId) {
        return HotelBooking.builder()
                .bookingId(generateBookingId())
                .userId(userId)
                .serviceProviderEmail(request.serviceProviderEmail())
                .roomId(request.roomId())
                .arrivalDate(request.arrivalDate())
                .departureDate(request.departureDate())
                .userFirstName(request.userFirstName())
                .userLastName(request.userLastName())
                .contactEmail(request.contactEmail())
                .contactTelephone(request.contactTelephone())
                .noOfBeds(request.noOfBeds())
                .specialRequest(request.specialRequest())
                .hotelPackage(request.hotelPackage())
                .bookingPrice(request.bookingPrice())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}

