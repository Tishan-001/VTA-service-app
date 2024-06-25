package com.vta.vtabackend.controllers;

import com.vta.vtabackend.documents.Vehicle;
import com.vta.vtabackend.dto.CreateVehicleRequest;
import com.vta.vtabackend.dto.VehicleAvailableRequest;
import com.vta.vtabackend.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @GetMapping("/")
    public  ResponseEntity<?> getAllVehicles(){
        List<Vehicle> vehicles= vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
    @GetMapping("/by-transport")
    public ResponseEntity<?> getVehiclesByTransport(@RequestHeader("Authorization")String token){
        if(token != null && !token.isEmpty()){
            token = token.substring(7);
            List<Vehicle> vehicles = vehicleService.getVehiclesByTransport(token);
            return ResponseEntity.ok(vehicles);
        }else{
            return ResponseEntity.badRequest().body("Invalid or missing token");
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@RequestBody CreateVehicleRequest request, @RequestHeader("Authorization")String token){
        token = token.substring(7);
        String response = vehicleService.saveVehicle(request,token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody CreateVehicleRequest request, @RequestHeader("Authorization")String token){
        token = token.substring(7);
        String response = vehicleService.updateVehicle(request,token);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicle(@RequestBody String vehicleId, @RequestHeader("Authorization") String token){
        token=token.substring(7);
        String response = vehicleService.deleteVehicle(vehicleId,token);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/available")
    public List<Vehicle> getAvailableVehicles(@RequestBody VehicleAvailableRequest request
                                              ) {
        return vehicleService.findAvailableVehicles(request);
    }
}
