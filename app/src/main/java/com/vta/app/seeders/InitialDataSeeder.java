package com.vta.app.seeders;

import com.vta.app.documents.UserDetails;
import com.vta.app.enums.Role;
import com.vta.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataSeeder implements ApplicationListener<ApplicationStartedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        userRepository.findByEmail("virtualtravelassistance@gmail.com")
                .switchIfEmpty(Mono.defer(this::createAdminUser))
                .subscribe();
    }

    private Mono<UserDetails> createAdminUser() {
        UserDetails userDetails = UserDetails.builder()
                .id(UUID.randomUUID().toString())
                .email("virtualtravelassistance@gmail.com")
                .password(passwordEncoder.encode("VTA@12345"))
                .role(Role.ADMIN)
                .build();
        return userRepository.save(userDetails)
                .doOnNext(user -> log.info("Admin user created successfully"));
    }
}
