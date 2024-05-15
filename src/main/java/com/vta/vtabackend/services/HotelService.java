package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.HotelRepository;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final JWTUtil jwtUtil;

    public String createHotel(CreateHotelRequest request) {
        boolean exits = hotelRepository.existsByEmail(request.email());
        if(exits) {
            return  "Hotel already registered";
        }
        else {
            Hotel hotel = Hotel.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .address(request.address())
                    .city(request.city())
                    .photo(request.photo())
                    .hotline(request.hotline())
                    .mobile(request.mobileNo())
                    .email(request.email())
                    .whatsapp(request.whatsapp())
                    .type(request.type())
                    .description(request.description())
                    .facilities(request.facilities())
                    .starRating(request.starRating())
                    .media(request.media())
                    .rooms(request.rooms())
                    .pricePerNight(request.pricePerNight())
                    .build();
            hotelRepository.save(hotel);
            return "Hotel profile create successfully";
        }
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotel(String email) {
        boolean exits = hotelRepository.existsByEmail(email);
        if (!exits) {
            throw new CustomException("Hotel profile can't bo found");
        }
        return hotelRepository.getHotelByEmail(email);
    }

    public String updateHotel(CreateHotelRequest request, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exits = hotelRepository.existsByEmail(request.email());
        Hotel hotel = hotelRepository.getHotelByEmail(request.email());

        if(exits) {
            if(Objects.equals(userId, hotel.getUserId())) {
                hotel.setName(request.name());
                hotel.setAddress(request.address());
                hotel.setCity(request.city());
                hotel.setPhoto(request.photo());
                hotel.setHotline(request.hotline());
                hotel.setMobile(request.mobileNo());
                hotel.setEmail(request.email());
                hotel.setWhatsapp(request.whatsapp());
                hotel.setType(request.type());
                hotel.setDescription(request.description());
                hotel.setFacilities(request.facilities());
                hotel.setMedia(request.media());
                hotel.setRooms(request.rooms());
                hotel.setStarRating(request.starRating());
                hotel.setPricePerNight(request.pricePerNight());
                hotelRepository.save(hotel);
                return "Hotel profile update successfully";
            } else {
                return "You can't access to this profile";
            }
        } else {
            return "Hotel profile can't bo found";
        }
    }

    public String deleteHotel(String email, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exits = hotelRepository.existsByEmail(email);
        Hotel hotel = hotelRepository.getHotelByEmail(email);
        if(exits) {
            if(Objects.equals(userId, hotel.getUserId())) {
                hotelRepository.deleteByEmail(email);
                return "Delete hotel profile";
            } else {
                return "You can't access to this profile";
            }
        } else {
            return "Hotel profile can't bo found";
        }
    }

    public String getHotelCount() {
        return String.valueOf(hotelRepository.count());
    }
}
