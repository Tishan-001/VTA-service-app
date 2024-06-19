package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.HotelBookingRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.*;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class HotelBookingService {
    private final HotelRepository hotelRepository;
    private final HotelBookingRepository hotelBookingRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String createBooking(HotelBookingRequest request, String token){

        if (!tokenService.isTokenExpired(token)) {
            throw new VTAException(VTAException.Type.UNAUTHORIZED,
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getMessage(),
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getCode());
        }

        String userEmail = tokenService.extractEmail(token);
        Users ExitUser = userRepository.getByEmail(userEmail);
        String userId = ExitUser.getId();

        Hotel service = hotelRepository.getHotelByEmail(request.serviceProviderEmail());
        if(service!=null){
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.HOTEL_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.HOTEL_NOT_FOUND.getCode());
        }
        Users user = userRepository.findById(userId).orElseThrow(()->
                new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.USER_NOT_FOUND.getCode()));
        try {
            HotelBooking hotelBooking = buildHotelBooking(request,userId);
            hotelBookingRepository.save(hotelBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.BOOKING_FAILED.getMessage(),
                    ErrorStatusCodes.BOOKING_FAILED.getCode());
        }
    }
    public List<HotelBooking> getBookingsByServiceProviderEmail(EmailRequest request){
        return hotelBookingRepository.getByServiceProviderEmail(request.getEmail())
                .orElseThrow(()-> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getMessage(),
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getCode()));
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
