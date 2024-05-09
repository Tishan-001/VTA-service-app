package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Transport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends MongoRepository<Transport,String> {
    boolean existsByEmail(String email);

    Optional<Transport> getTransportationByEmail(String email);
}
