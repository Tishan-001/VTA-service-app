package com.vta.vtabackend.repositories;

import com.vta.vtabackend.documents.UserVerificationCode;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface UserVerificationCodesRepository extends MongoRepository<UserVerificationCode, String> {
    Optional<UserVerificationCode> findByCode(String code);

}
