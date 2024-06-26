package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.TourGuide;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourGuideRepository extends MongoRepository<TourGuide, String> {
    boolean existsByEmail(String email);

    TourGuide getTourguideByEmail(String email);

    TourGuide getTourguideById(String id);

    TourGuide getTourguideByUserId(String id);
}
