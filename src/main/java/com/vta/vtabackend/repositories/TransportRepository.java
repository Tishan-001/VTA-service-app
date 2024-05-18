package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Transport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends MongoRepository<Transport,String> {
    boolean existsByEmail(String email);
    Transport getTransportationByEmail(String email);
    Transport getTransportationById(String id);
}
