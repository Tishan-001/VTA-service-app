package com.vta.app.repositories;

import com.vta.app.documents.UserVerificationCode;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserVerificationCodesRepository extends ReactiveMongoRepository<UserVerificationCode, String> {
    Mono<UserVerificationCode> findByContactMedium(String contactMedium);
}
