package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TourGuide;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourGuideRepository extends MongoRepository<TourGuide, String> {
    boolean existsByEmail(String email);

    TourGuide getTourguideByEmail(String email);
}
