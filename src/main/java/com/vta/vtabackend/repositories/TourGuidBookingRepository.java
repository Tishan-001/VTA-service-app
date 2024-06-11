package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TourGuideBooking;
import com.vta.vtabackend.documents.TransportBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TourGuidBookingRepository extends MongoRepository<TourGuideBooking,String> {
    Optional<List<TourGuideBooking>> getByServiceProviderById(String email);
}
