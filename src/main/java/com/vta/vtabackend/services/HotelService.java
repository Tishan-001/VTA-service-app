package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.HotelRepository;
import com.vta.vtabackend.utils.JWTUtil;
import jakarta.validation.constraints.Email;
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

    public String createHotel(CreateHotelRequest request, String token) {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exits = hotelRepository.existsByEmail(request.email());
        if(exits) {
            return  "Hotel already registered using this email";
        }
        else {
            Hotel hotel = Hotel.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .address(request.address())
                    .district(request.district())
                    .country(request.country())
                    .hotline(request.hotline())
                    .mobile(request.mobileNo())
                    .email(request.email())
                    .whatsapp(request.whatsapp())
                    .type(request.type())
                    .description(request.description())
                    .facilities(request.facilities())
                    .starRating(request.starRating())
                    .pricePerNight(request.pricePerNight())
                    .userId(userId)
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
                hotel.setDistrict(request.district());
                hotel.setCountry(request.country());
                hotel.setHotline(request.hotline());
                hotel.setMobile(request.mobileNo());
                hotel.setEmail(request.email());
                hotel.setWhatsapp(request.whatsapp());
                hotel.setType(request.type());
                hotel.setDescription(request.description());
                hotel.setFacilities(request.facilities());
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
}
