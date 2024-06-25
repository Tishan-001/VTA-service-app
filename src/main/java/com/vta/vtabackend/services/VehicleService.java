package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.TransportBooking;
import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.documents.Vehicle;
import com.vta.vtabackend.dto.CreateVehicleRequest;
import com.vta.vtabackend.dto.VehicleAvailableRequest;
import com.vta.vtabackend.repositories.TransportBookingRepository;
import com.vta.vtabackend.repositories.TransportRepository;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final TransportRepository transportRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final VehicleRepository vehicleRepository;
    private TransportBookingRepository transportBookingRepository;

    @Autowired
    public VehicleService(TransportRepository transportRepository, UserRepository userRepository, TokenService tokenService, VehicleRepository vehicleRepository) {
        this.transportRepository = transportRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByTransport(String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);
        return vehicleRepository.getVehicleByTransportId(user.getId());
    }

    public String saveVehicle(CreateVehicleRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        if (transportRepository.existsById(user.getId())) {
            Transport transport = transportRepository.getTransportationById(user.getId());
            Vehicle newVehicle = new Vehicle(
                    transport.getId(),
                    request.type(),
                    request.vehicleCategory(),
                    request.photo(),
                    request.price(),
                    request.features(),
                   request.location()
            );
            vehicleRepository.save(newVehicle);
            return "Vehicle added successfully!";
        } else {
            return "Transport service not found!";
        }
    }

    public String updateVehicle(CreateVehicleRequest request, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        if (transportRepository.existsById(user.getId())) {
            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(request.id());
            if (vehicleOpt.isPresent()) {
                Vehicle vehicle = vehicleOpt.get();
                if (Objects.equals(user.getId(), vehicle.getTransportId())) {
                    vehicle.setType(request.type());
                    vehicle.setVehicleCategory(request.vehicleCategory());
                    vehicle.setPhoto(request.photo());
                    vehicle.setPrice(request.price());
                    vehicle.setFeatures(request.features());
                    vehicleRepository.save(vehicle);
                    return "Vehicle updated successfully!";
                } else {
                    return "You don't have access to this vehicle.";
                }
            } else {
                return "Vehicle not found.";
            }
        } else {
            return "Transport service not found.";
        }
    }

    public String deleteVehicle(String id, String token) {
        String userEmail = tokenService.extractEmail(token);
        Users user = userRepository.getByEmail(userEmail);

        if (vehicleRepository.existsById(id)) {
            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
            if (vehicleOpt.isPresent()) {
                Vehicle vehicle = vehicleOpt.get();
                if (Objects.equals(user.getId(), vehicle.getTransportId())) {
                    vehicleRepository.deleteById(id);
                    return "Vehicle deleted successfully!";
                } else {
                    return "You don't have access to delete this vehicle.";
                }
            } else {
                return "Vehicle not found.";
            }
        } else {
            return "Vehicle not found.";
        }
    }

    public List<Vehicle> findAvailableVehicles(VehicleAvailableRequest request) {
        List<Vehicle> vehicles = vehicleRepository.findVehiclesByCategoryAndLocation(request.category(), request.location());
        return vehicles;
//        return vehicles.stream()
//                .filter(vehicle -> {
//                    System.out.println("vehicle: "+vehicle);
//                    List<TransportBooking> bookings = transportBookingRepository.findBookedVehicles(vehicle.getId(), request.bookingStartDate(), request.bookingEndDate());
//                    return bookings.isEmpty();
//                })
//                .collect(Collectors.toList());
    }
}
