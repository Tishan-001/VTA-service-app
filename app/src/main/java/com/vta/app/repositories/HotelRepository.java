package com.vta.app.repositories;

import com.vta.app.documents.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface HotelRepository extends ReactiveMongoRepository<Hotel, String> {
    Mono<Hotel> findByUserId(String userId);
    Mono<Boolean> existsByUserId(String userId);
}
