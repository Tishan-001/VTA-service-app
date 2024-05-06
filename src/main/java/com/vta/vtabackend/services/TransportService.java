package com.vta.vtabackend.services;

import com.vta.vtabackend.components.PBKDF2Encoder;
import com.vta.vtabackend.components.SecurityContextRepository;
import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.UserVerificationCode;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.utils.JWTUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TransportService {
    private TransportRepository transportRepository;
    private PBKDF2Encoder passwordEncoder;
    private JWTUtil jwtUtil;

    public String saveTransportationDetails(RegisterTransportRequest request){
        boolean exists = transportRepository.existsByEmail(request.email());
        if(exists){
            return "Email already exists";
        }
        else {
            Transport transport = Transport.builder()
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .email(request.email())
                    .mobile(request.mobile())
                    .vehicleCategory(request.vehicleCategory())
                    .price(request.price())
                    .address(request.address())
                    .description(request.description())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
            transportRepository.save(transport);

            return "Your details successfully saved!";
        }
    }
    public List<Transport> getTransports() {return transportRepository.findAll();}
    public Transport getTransport(String email){
        boolean exists= transportRepository.existsByEmail(email);
        if(!exists){
            throw new CustomException("Email does not exists");
        }else{
            return transportRepository.getTransportationByEmail(email);
        }

    }

    public String deleteTransport(String email,String token){
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exists = transportRepository.existsByEmail(email);

        if (exists) {
            if(Objects.equals(userId, transportRepository.getTransportationByEmail(email).getId())) {
                transportRepository.deleteById(email);
                return "Successfully deleted transport";
            } else {
                return "You can't delete this transport";
            }
        } else {
            return "Tour Guide does not exist";
        }
    }
}
