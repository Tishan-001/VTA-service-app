package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.dto.CreateRoomRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.HotelRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String createHotel(CreateHotelRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exits = hotelRepository.existsByEmail(request.email());
        if(exits) {
            return  "Hotel already registered";
        }
        else {
            Hotel hotel = Hotel.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .address(request.address())
                    .photo(request.media().get(0))
                    .city(request.city())
                    .hotline(request.hotline())
                    .mobile(request.mobileNo())
                    .email(request.email())
                    .whatsapp(request.whatsapp())
                    .description(request.description())
                    .media(request.media())
                    .pricePerNight(request.pricePerNight())
                    .userId(user.getId())
                    .build();
            hotelRepository.save(hotel);
            return "Hotel profile create successfully";
        }
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotel(String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (Objects.isNull(hotel)) {
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.HOTEL_PROFILE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.HOTEL_PROFILE_NOT_FOUND.getCode());
        }
        return hotel;
    }

    public String updateHotel(CreateHotelRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exits = hotelRepository.existsByEmail(request.email());
        Hotel hotel = hotelRepository.getHotelByEmail(request.email());

        if(exits) {
            if(Objects.equals(user.getId(), hotel.getUserId())) {
                hotel.setName(request.name());
                hotel.setAddress(request.address());
                hotel.setCity(request.city());
                hotel.setHotline(request.hotline());
                hotel.setMobile(request.mobileNo());
                hotel.setEmail(request.email());
                hotel.setWhatsapp(request.whatsapp());
                hotel.setDescription(request.description());
                hotel.setMedia(request.media());
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

    public String deleteHotel(String id, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exits = hotelRepository.findById(id).isPresent();
        Hotel hotel = hotelRepository.findById(id).get();
        if(exits) {
            if(Objects.equals(user.getId(), hotel.getUserId())) {
                hotelRepository.deleteById(id);
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

    public String addRoom(CreateRoomRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (!Objects.isNull(hotel)) {
            // Initialize the rooms list if it is null
            List<Hotel.Room> rooms = hotel.getRooms();
            if (rooms == null) {
                rooms = new ArrayList<>();
            }

            // Create a new Room object from the request data
            Hotel.Room newRoom = new Hotel.Room(
                    request.name(),
                    request.type(),
                    request.photo(),
                    request.price(),
                    request.facilities(),
                    request.bedCount()
            );

            // Add the new room to the hotel's room list
            rooms.add(newRoom);
            hotel.setRooms(rooms);

            // Save the updated hotel back to the database
            hotelRepository.save(hotel);

            return "Room added successfully!";
        } else {
            return "Hotel not found!";
        }
    }

    public String deleteRoom(String roomId, String token) {

        String userEmail = tokenService.extractEmail(token);

        Users user = userRepository.getByEmail(userEmail);

        Hotel hotel = hotelRepository.findByUserId(user.getId());

        if (hotel != null) {

            List<Hotel.Room> rooms = hotel.getRooms();

            // Find and remove the room with the given roomId
            boolean removed = rooms.removeIf(room -> room.getId().equals(roomId));

            if (removed) {
                // Update the hotel's rooms list and save it back to the database
                hotel.setRooms(rooms);
                hotelRepository.save(hotel);
                return "Room deleted successfully!";
            } else {
                return "Room not found!";
            }
        } else {
            return "Hotel not found!";
        }
    }

    public List<Hotel.Room> getHotelRooms(String token) {
        String userEmail = tokenService.extractEmail(token);

        Users user = userRepository.getByEmail(userEmail);

        Hotel hotel = hotelRepository.findByUserId(user.getId());

        if (!Objects.isNull(hotel)) {
            return hotel.getRooms();
        } else {
            throw new VTAException(
                    VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.HOTEL_PROFILE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.HOTEL_PROFILE_NOT_FOUND.getCode());
        }
    }

    public Hotel.Room getRoom(String id) {
        List<Hotel> hotels = hotelRepository.findAll();
        for (Hotel hotel : hotels) {
            List<Hotel.Room> rooms = hotel.getRooms();
            for (Hotel.Room room : rooms) {
                if (room.getId().equals(id)) {
                    return room;
                }
            }
        }
        return null;
    }

    public String updateRoom(String id, CreateRoomRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (!Objects.isNull(hotel)) {
            List<Hotel.Room> rooms = hotel.getRooms();
            for (Hotel.Room room : rooms) {
                if (room.getId().equals(id)) {
                    room.setName(request.name());
                    room.setType(request.type());
                    room.setPhoto(request.photo());
                    room.setPrice(request.price());
                    room.setFacilities(request.facilities());
                    room.setBedCount(request.bedCount());
                }
            }
        }

        hotelRepository.save(hotel);
        return "Room updated successfully!";
    }
}
