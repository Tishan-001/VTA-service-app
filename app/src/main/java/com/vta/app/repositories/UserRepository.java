package com.vta.app.repositories;

import com.vta.app.documents.UserDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserDetails, String> {
    Mono<UserDetails> findByEmail(String email);
    Mono<UserDetails> findByMobile(String mobile);
    Mono<Boolean> existsByEmail(String email);
}
