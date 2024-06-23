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

        Transport service = transportRepository.getTransportationById(request.serviceProviderId());
        if(service==null){
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        }
        try {
            TransportBooking transportBooking = buildTransportBooking(request,user.getId(),userEmail);
            transportBookingRepository.save(transportBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.BOOKING_FAILED.getMessage(),
                    ErrorStatusCodes.BOOKING_FAILED.getCode());
        }
    }
    public List<TransportBooking> getBookingsService(String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        return transportBookingRepository.getByServiceProviderId(user.getId())
                .orElseThrow(()-> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getMessage(),
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getCode()));
    }

    public List<TransportBooking> getBookingsByUser(String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        return transportBookingRepository.getByUserId(user.getId())
                .orElseThrow(()-> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getMessage(),
                        ErrorStatusCodes.BOOKING_NOT_AVAILABLE.getCode()));
    }

    private TransportBooking buildTransportBooking(TransportBookingRequest request, String userId, String userEmail) {
        return TransportBooking.builder()
                .bookingId(generateBookingId())
                .userId(userId)
                .userName(request.userName())
                .contactNo(request.contactNo())
                .userEmail(userEmail)
                .pickUpLocation(request.pickUpLocation())
                .dropOffLocation(request.dropOffLocation())
                .bookingStartDate(request.bookingStartDate())
                .bookingEndDate(request.bookingEndDate())
                .bookingPrice(request.bookingPrice())
                .serviceProviderId(request.serviceProviderId())
                .vehicleId(request.vehicleID())
                .withDriver(request.withDriver())
                .build();
    }

    private String generateBookingId() {
        return UUID.randomUUID() + "-" + System.currentTimeMillis();
    }
}
