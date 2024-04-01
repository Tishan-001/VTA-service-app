package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserDetails, String> {
    Optional<UserDetails> findByEmail(String email);
    UserDetails findByMobile(String mobile);
    boolean existsByEmail(String email);
}
