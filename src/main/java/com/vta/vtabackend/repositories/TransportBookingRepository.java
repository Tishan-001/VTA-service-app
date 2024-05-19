package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TransportBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransportBookingRepository extends MongoRepository<TransportBooking,String> {

    Optional<List<TransportBooking>> getByServiceProviderEmail(String email);
}
