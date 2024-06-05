package com.vta.vtabackend.services;

import com.vta.vtabackend.components.PBKDF2Encoder;
import com.vta.vtabackend.components.SecurityContextRepository;
import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.UserVerificationCode;
import com.vta.vtabackend.dto.AuthResponse;
import com.vta.vtabackend.dto.LoginWithEmailRequest;
import com.vta.vtabackend.dto.RegisterTransportRequest;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.exceptions.ApiException;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final PBKDF2Encoder passwordEncoder;
    private final JWTUtil jwtUtil;

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
    public AuthResponse loginWithEmail(LoginWithEmailRequest request) throws ApiException {
        Transport transport = transportRepository.getTransportationByEmail(request.email()).orElseThrow(() ->
                new CustomException("User not found with email : "+request.email()));
        if(!passwordEncoder.matches(request.password(), transport.getPassword())){
            throw new CustomException("Incorrect password");
        }
//        if (!transport.isVerified()) {
//            throw new CustomException("User not verified: " + request.email());
//        }
        return new AuthResponse(jwtUtil.generateTokenForTransport(transport));

    }
    public List<Transport> getTransports() {return transportRepository.findAll();}

    public Transport getTransport(String email){
        return transportRepository.getTransportationByEmail(email).orElseThrow(()->
                new CustomException("Email does not exists"));
    }

    public String updateTransport(RegisterTransportRequest request, String token)
    {
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        Transport transport = transportRepository.getTransportationByEmail(request.email()).orElseThrow(()->
                new CustomException("Transport not found by email: "+ request.email()));
        if(!Objects.equals(userId, transport.getId())){
            return "Unauthorized access: You can not upadate this trasportaion detail.";
        }else {
            transport.setName(request.name() != null ? request.name() : transport.getName());
            transport.setMobile(request.mobile() != null ? request.mobile() : transport.getName());
            transport.setAddress(request.address() != null ? request.address() : transport.getAddress());
            transport.setPrice(request.price()!= null ? request.price() : transport.getPrice());
            transport.setFeatures(request.features() != null ? request.features() : transport.getFeatures());
            transport.setVehicleCategory(request.vehicleCategory() != null ? request.vehicleCategory() : transport.getVehicleCategory());
            transport.setPassword(passwordEncoder.encode(request.password() != null ? request.password() : transport.getPassword()));

            transportRepository.save(transport);
            return "Tour guide updated successfully.";
        }

    }

    public String deleteTransport(String email,String token){
        String userId = jwtUtil.getUserIdFromToken(token.substring(7));
        boolean exists = transportRepository.existsByEmail(email);
        Transport transport= transportRepository.getTransportationByEmail(email).orElseThrow(()->
                new CustomException("Transportation does not exist"));
        if(Objects.equals(userId, transport.getId())) {
            transportRepository.deleteById(transport.getId());
            return "Successfully deleted transport";
        } else {
            return "You can't delete this transport";
        }

    }
}
