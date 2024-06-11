package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.HotelBooking;
import com.vta.vtabackend.documents.TourGuideBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HotelBookingRepository extends MongoRepository<HotelBooking,String> {
    Optional<List<HotelBooking>> getByServiceProviderEmail(String email);
}
