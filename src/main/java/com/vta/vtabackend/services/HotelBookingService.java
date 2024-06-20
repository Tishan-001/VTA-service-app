package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.*;
import com.vta.vtabackend.dto.HotelBookingRequest;
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
public class HotelBookingService {
    private final HotelRepository hotelRepository;
    private final HotelBookingRepository hotelBookingRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String createBooking(HotelBookingRequest request, String token){

        if (tokenService.isTokenExpired(token)) {
            throw new VTAException(VTAException.Type.UNAUTHORIZED,
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getMessage(),
                    ErrorStatusCodes.TOKEN_EXPIRED_PLEASE_TRY_AGAIN.getCode());
        }

        String userEmail = tokenService.extractEmail(token);
        Users ExitUser = userRepository.getByEmail(userEmail);
        String userId = ExitUser.getId();

        String roomId = request.roomId();

        HotelBooking hotelBooking = HotelBooking.builder()
                .bookingId(UUID.randomUUID().toString())
                .roomId(roomId)
                .userId(userId)
                .arrivalDate(request.arrivalDate())
                .departureDate(request.departureDate())
                .userFirstName(request.userFirstName())
                .userLastName(request.userLastName())
                .contactEmail(request.contactEmail())
                .contactTelephone(request.contactTelephone())
                .noOfBeds(request.noOfBeds())
                .specialRequest(request.specialRequest())
                .bookingPrice(request.bookingPrice())
                .build();

        List<Hotel> hotels = hotelRepository.findAll();
        for (Hotel hotel : hotels) {
            List<Hotel.Room> rooms = hotel.getRooms();
            for (Hotel.Room room : rooms) {
                if (room.getId().equals(roomId)) {
                    room.setIsAvailable(false);
                    hotelBooking.setRoomName(room.getName());
                    hotelRepository.save(hotel);
                    hotelBooking.setHotelId(hotel.getId());
                } else {
                    throw new VTAException(VTAException.Type.NOT_FOUND,
                            ErrorStatusCodes.HOTEL_NOT_FOUND.getMessage(),
                            ErrorStatusCodes.HOTEL_NOT_FOUND.getCode());
                }
            }
        }

        try {
            hotelBookingRepository.save(hotelBooking);
            return "Your booking is successful";
        }
        catch (Exception e){
            throw new VTAException(VTAException.Type.EXTERNAL_SYSTEM_ERROR,
                    ErrorStatusCodes.BOOKING_FAILED.getMessage(),
                    ErrorStatusCodes.BOOKING_FAILED.getCode());
        }
    }
    public List<HotelBooking> getBookingDetails(String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        Hotel hotel = hotelRepository.findByUserId(user.getId());

        return hotelBookingRepository.findByHotelId(hotel.getId());
    }

}
