package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.exceptions.VTAException;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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

    public Transport getTransportByToken(String token){
        try {
            String userEmail = tokenService.extractEmail(token);
            System.out.println("Extracted Email: " + userEmail);

            Users user = userRepository.getByEmail(userEmail);
            System.out.println("Retrieved User: " + user);

            boolean exists = transportRepository.existsById(user.getId());
            System.out.println("Transport Exists: " + exists);

            if (exists) {
                System.out.println("User found");
                Transport transport = transportRepository.getTransportationById(user.getId());
                System.out.println("Retrieved Transport: " + transport);

                return transport;
            } else {
                System.out.println("Transport not found for user ID: " + user.getId());
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