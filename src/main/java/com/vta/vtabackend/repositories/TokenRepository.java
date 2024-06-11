package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
