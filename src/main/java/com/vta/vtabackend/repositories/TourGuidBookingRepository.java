package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TourGuideBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourGuidBookingRepository extends MongoRepository<TourGuideBooking,String> {
    List<TourGuideBooking> findAllByTourGuideId(String id);
}
