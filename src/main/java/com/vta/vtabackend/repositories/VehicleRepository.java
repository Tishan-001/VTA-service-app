package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String> {
   Vehicle getVehicleById(String id);
   List<Vehicle> getVehicleByTransportId(String transportId);

   @Query("{ 'vehicleCategory': ?0, 'location': ?1 }")
   List<Vehicle> findVehiclesByCategoryAndLocation(String vehicleCategory, String location);

}

