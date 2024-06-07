package com.vta.vtabackend.seeders;

import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.enums.Role;
import com.vta.vtabackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            userRepository.findByEmail("virtualtravelassistance@gmail.com")
                    .ifPresentOrElse(user -> log.info("Admin user already exists."),
                            () -> {
                                Users users = Users.builder()
                                        .id(UUID.randomUUID().toString())
                                        .email("virtualtravelassistance@gmail.com")
                                        .password(passwordEncoder.encode("VTA@12345"))
                                        .role(Role.ADMIN)
                                        .build();
                                userRepository.save(users);
                                log.info("Admin user created successfully");
                            });
        } catch (Exception e) {
            log.error("Error creating admin user: ", e);
        }
    }
}
