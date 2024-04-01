package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HotelRepository extends MongoRepository<Hotel, String> {
    boolean existsByEmail(String email);

    Hotel getHotelByEmail(String email);

    void deleteByEmail(String email);
}
