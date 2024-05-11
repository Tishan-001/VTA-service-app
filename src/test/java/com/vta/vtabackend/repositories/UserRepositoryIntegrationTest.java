package com.vta.vtabackend.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.vta.vtabackend.documents.UserDetails;
import com.vta.vtabackend.enums.Role;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserDetails_savesAndRetrievesUserCorrectly() {
        // Arrange
        UserDetails user = UserDetails.builder()
                .id("newUserId")
                .name("Alice Johnson")
                .email("alice.johnson@example.com")
                .mobile("9876543210")
                .password("securePasswordHash")
                .role(Role.ADMIN)
                .verified(false)
                .build();

        // Act
        userRepository.save(user);

        // Assert
        Optional<UserDetails> retrievedUser = userRepository.findById("newUserId");
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getId()).isEqualTo("newUserId");
        assertThat(retrievedUser.get().getName()).isEqualTo("Alice Johnson");
        assertThat(retrievedUser.get().getEmail()).isEqualTo("alice.johnson@example.com");
        assertThat(retrievedUser.get().getMobile()).isEqualTo("9876543210");
        assertThat(retrievedUser.get().getPassword()).isEqualTo("securePasswordHash");
        assertThat(retrievedUser.get().getRole()).isEqualTo(Role.ADMIN);
        assertThat(retrievedUser.get().isVerified()).isEqualTo(false);
    }

    @Test
    public void findByEmail_returnsUserDetails() {
        // Arrange
        UserDetails user = UserDetails.builder()
                .id("userId")
                .name("John Doe")
                .email("john.doe@example.com")
                .password("passwordHash")
                .role(Role.USER)
                .verified(true)
                .build();
        userRepository.save(user);

        // Act
        Optional<UserDetails> foundUser = userRepository.findByEmail("john.doe@example.com");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void findByEmail_returnsEmptyIfNotFound() {
        // Act
        Optional<UserDetails> foundUser = userRepository.findByEmail("nobody@example.com");

        // Assert
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void findByMobile_returnsUserDetails() {
        // Arrange
        UserDetails user = UserDetails.builder()
                .id("userId")
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .mobile("1234567890")
                .password("passwordHash")
                .role(Role.USER)
                .verified(true)
                .build();
        userRepository.save(user);

        // Act
        UserDetails foundUser = userRepository.findByMobile("1234567890");

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getMobile()).isEqualTo("1234567890");
    }

    @Test
    public void existsByEmail_whenUserExists_returnsTrue() {
        // Arrange
        UserDetails user = UserDetails.builder()
                .id("userId")
                .name("Bob Smith")
                .email("bob@example.com")
                .password("passwordHash")
                .role(Role.USER)
                .verified(true)
                .build();
        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByEmail("bob@example.com");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByEmail_whenUserDoesNotExist_returnsFalse() {
        // Act
        boolean exists = userRepository.existsByEmail("alice@example.com");

        // Assert
        assertThat(exists).isFalse();
    }
}
