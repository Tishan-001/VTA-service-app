package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Hotel;
import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.CreateHotelRequest;
import com.vta.vtabackend.dto.CreateRoomRequest;
import com.vta.vtabackend.dto.CreateVehicleRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.enums.VehicleCategory;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String saveTransportationDetails(RegisterTransportRequest request, String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exists = transportRepository.existsByEmail(request.email());
        if(exists){
            return "Email already exists";
        }
        else {
            Transport transport = Transport.builder()
                    .id(user.getId())
                    .name(request.name())
                    .email(request.email())
                    .mobile(request.mobile())
                    .address(request.address())
                    .description(request.description())
                    .imageUrl(request.imageUrl())
                    .build();
            transportRepository.save(transport);

            return "Your details successfully saved!";
        }
    }

    public List<Transport> getTransports() {return transportRepository.findAll();}

    public Transport getTransport(String id){
        boolean exists= transportRepository.existsById(id);
        if(!exists){
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TRANSPORT_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TRANSPORT_NOT_FOUND.getCode());
        }else{
            System.out.println("trandport id:" +id);
            return transportRepository.getTransportationById(id);
        }

    }

    public String deleteTransport(String email,String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exists = transportRepository.existsByEmail(email);

        if (exists) {
            if(Objects.equals(user.getId(), transportRepository.getTransportationByEmail(email).getId())) {
                transportRepository.deleteById(email);
                return "Successfully deleted transport";
            } else {
                return "You can't delete this transport";
            }
        } else {
            return "Tour Guide does not exist";
        }
    }
    public String getTransportCount() {
        return String.valueOf(transportRepository.count());
    }
    public Transport getTransportByToken(String token){
        try {
            String userEmail = tokenService.extractEmail(token);

            Users user = userRepository.getByEmail(userEmail);

            boolean exists = transportRepository.existsById(user.getId());

            if (exists) {
                Transport transport = transportRepository.getTransportationById(user.getId());
                return transport;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            throw new VTAException(VTAException.Type.NOT_FOUND,
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getMessage(),
                    ErrorStatusCodes.TOURGUIDE_NOT_FOUND.getCode());
        }

    }


}