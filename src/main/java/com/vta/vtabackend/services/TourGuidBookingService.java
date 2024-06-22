package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.TourGuideBookingRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.*;
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
    private final MailService mailService;

    public String createBooking(TourGuideBookingRequest request, String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        TourGuide tourGuide = tourGuideRepository.getTourguideById(request.tourGuideId());

        TourGuideBooking tourGuideBooking = TourGuideBooking.builder()
                .bookingId(UUID.randomUUID().toString())
                .userId(user.getId())
                .tourGuideId(tourGuide.getId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endDate(request.endDate())
                .endTime(request.endTime())
                .specialRequest(request.specialRequest())
                .price(request.bookingPrice())
                .build();

        tourGuidBookingRepository.save(tourGuideBooking);
        mailService.tourguideBooking(request.email(), request.firstName(), tourGuide.getName(), request.startDate(), request.startTime(), request.endDate(), request.endTime());
        return "Booking Successful";
    }
    public List<TourGuideBooking> getBookingsByEmail(String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        TourGuide tourGuide = tourGuideRepository.getTourguideByUserId(user.getId());

        if(tourGuide == null){
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        } else {
            return tourGuidBookingRepository.findAllByTourGuideId(tourGuide.getId());
        }
    }
}

