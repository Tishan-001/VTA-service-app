package com.vta.vtabackend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Optional;

import com.vta.vtabackend.documents.UserVerificationCode;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
public class UserVerificationCodesRepositoryIntegrationTest {

    @Autowired
    private UserVerificationCodesRepository repository;

    @Test
    public void findByCode_returnsVerificationCode() {
        // Arrange
        UserVerificationCode code = new UserVerificationCode("1", "123456", "email@example.com", System.currentTimeMillis() + 10000);
        repository.save(code);

        // Act
        Optional<UserVerificationCode> foundCode = repository.findByCode("123456");

        // Assert
        assertThat(foundCode).isPresent();
        assertThat(foundCode.get().getCode()).isEqualTo("123456");
    }

    @Test
    public void findByCode_returnsEmptyIfNotFound() {
        // Arrange - no setup needed as we are looking for a non-existing code

        // Act
        Optional<UserVerificationCode> foundCode = repository.findByCode("456789");

        // Assert
        assertThat(foundCode).isNotPresent();
    }
}

