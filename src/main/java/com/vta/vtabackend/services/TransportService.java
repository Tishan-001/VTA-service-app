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

    public String saveVehicle(CreateVehicleRequest request, String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        Transport transport = transportRepository.getTransportationById(user.getId());
        if (!Objects.isNull(transport)) {
            // Initialize the vehicle list if it is null
            List<Transport.Vehicle> vehicles = transport.getVehicles();
            if (vehicles == null) {
                vehicles = new ArrayList<>();
            }

            // Create a new Vehicle object from the request data
            Transport.Vehicle newVehicle = new Transport.Vehicle(
                    request.type(),
                    request.vehicleCategory(),
                    request.photo(),
                    request.price(),
                    request.features()
            );

            // Add the new Vehicle to the transport's vehicle list
            vehicles.add(newVehicle);
            transport.setVehicles(vehicles);

            // Save the updated transport back to the database
            transportRepository.save(transport);

            return "Vehicle added successfully!";
        } else {
            return "Transport Service not found!";
        }

    }

    public String updateVehicle(CreateVehicleRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exits = transportRepository.existsById(user.getId());

        if(exits) {
            Transport transport = transportRepository.getTransportationById(user.getId());
            for(Transport.Vehicle vehicle : transport.getVehicles()) {
                if (Objects.equals(request.id(), vehicle.getId())) {
                    vehicle.setType(request.type());
                    vehicle.setVehicleCategory(request.vehicleCategory());
                    vehicle.setPhoto(request.photo());
                    vehicle.setPrice(request.price());
                    vehicle.setFeatures(request.features());
                    transportRepository.save(transport);
                    return "Vehicle updated successfully";

                } else {
                    return "You can't access to this vehicle";
                }
            }
        } else {
            return "Vehicle didn't find";
        }
        return "Vehicle does not  exist";
    }

    public String deleteVehicle(String id, String token){
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        boolean exits = transportRepository.existsById(user.getId());
        if(exits) {
            Transport transport = transportRepository.getTransportationById(user.getId());
            transport.removeVehicleById(id);
            transportRepository.save(transport);
            return "Vehicle deleted successfully!";
        } else {
            return "Vehicle didn't find";
        }
    }
}