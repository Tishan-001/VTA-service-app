package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.TransportBooking;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.TransportBookingRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TransportBookingRepository;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.response.EmailRequest;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportBookingService {
    private final TransportRepository transportRepository;
    private final TransportBookingRepository transportBookingRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    public String createBooking(TransportBookingRequest request, String token){

        if (tokenService.isTokenExpired(token)) {
            throw new VTAException(VTAException.Type.UNAUTHORIZED,
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getMessage(),
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getCode());
        }

        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        Transport service = transportRepository.getTransportationByEmail(request.serviceProviderId());

        try {
            TransportBooking transportBooking = buildTransportBooking(request,user.getId());
            transportBookingRepository.save(transportBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.BOOKING_FAILED.getMessage(),
                    ErrorStatusCodes.BOOKING_FAILED.getCode());
        }
    }
    public List<TransportBooking> getBookingsByEmail(EmailRequest request){
        return transportBookingRepository.getByServiceProviderEmail(request.getEmail())
                .orElseThrow(()-> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getMessage(),
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getCode()));
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
                .serviceProviderId(request.serviceProviderId())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}
