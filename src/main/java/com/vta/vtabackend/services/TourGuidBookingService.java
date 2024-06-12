package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.TourGuideBookingRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.*;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.utils.ErrorStatusCodes;
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
    private final TokenService tokenService;

    public String createBooking(TourGuideBookingRequest request, String token){

        if (tokenService.isTokenExpired(token)) {
            throw new VTAException(VTAException.Type.UNAUTHORIZED,
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getMessage(),
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getCode());
        }
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        TourGuide service = tourGuideRepository.getTourguideById(request.serviceProviderId());
        if(service==null){
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        }

        try {
            TourGuideBooking tourGuideBooking = buildTourGuideBooking(request,user.getId());
            tourGuidBookingRepository.save(tourGuideBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.BOOKING_FAILED.getMessage(),
                    ErrorStatusCodes.BOOKING_FAILED.getCode());
        }
    }
    public List<TourGuideBooking> getBookingsByEmail(EmailRequest request){
        return tourGuidBookingRepository.getByServiceProviderByEmail(request.getEmail())
                .orElseThrow(()-> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getMessage(),
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getCode()));
    }

    private TourGuideBooking buildTourGuideBooking(TourGuideBookingRequest request, String userId) {
        return TourGuideBooking.builder()
                .bookingId(generateBookingId())
                .userId(userId)
                .Location(request.Location())
                .bookingStartDate(request.bookingStartDate())
                .bookingEndDate(request.bookingEndDate())
                .bookingPrice(request.bookingPrice())
                .userContact(request.userContact())
                .serviceProviderID(request.serviceProviderId())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}

