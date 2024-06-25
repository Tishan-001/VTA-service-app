package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TransportBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransportBookingRepository extends MongoRepository<TransportBooking,String> {

    Optional<List<TransportBooking>> getByServiceProviderId(String id);
    Optional<List<TransportBooking>> getByUserId(String id);
    @Query("{ 'vehicleId': ?0, $or: [ { 'bookingStartDate': { $lte: ?2 }, 'bookingEndDate': { $gte: ?1 } }, { 'bookingStartDate': { $gte: ?1, $lte: ?2 } }, { 'bookingEndDate': { $gte: ?1, $lte: ?2 } } ] }")
    List<TransportBooking> findBookedVehicles(String vehicleId, String bookingStartDate, String bookingEndDate);
}
