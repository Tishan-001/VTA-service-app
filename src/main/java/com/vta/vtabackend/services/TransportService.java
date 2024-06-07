package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                    .id(UUID.randomUUID().toString())
                    .name(request.name())
                    .email(request.email())
                    .mobile(request.mobile())
                    .vehicleCategory(request.vehicleCategory())
                    .price(request.price())
                    .address(request.address())
                    .description(request.description())
                    .userId(user.getId())
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
}