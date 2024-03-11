package com.vta.app.repositories;

import com.vta.app.documents.Config;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ConfigsRepository extends ReactiveMongoRepository<Config, String> {
    Mono<Boolean> existsByName(String name);
}
