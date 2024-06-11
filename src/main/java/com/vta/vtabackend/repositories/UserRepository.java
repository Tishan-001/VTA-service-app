package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Users findByMobile(String mobile);
    boolean existsByEmail(String email);
    Users getByEmail(String userEmail);
}
