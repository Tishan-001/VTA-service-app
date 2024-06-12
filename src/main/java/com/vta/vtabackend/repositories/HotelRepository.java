package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    boolean existsByEmail(String email);

    Hotel getHotelByEmail(String email);

    void deleteByEmail(String email);

    Hotel findByUserId(String userId);
}
