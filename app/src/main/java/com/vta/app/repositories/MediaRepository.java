package com.vta.app.repositories;

import com.vta.app.documents.Media;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MediaRepository extends ReactiveMongoRepository<Media, String> {
}
