package com.vta.vtabackend.security;

import com.vta.vtabackend.documents.Users;
import com.vta.vtabackend.repositories.UserRepository;
import com.vta.vtabackend.utils.ErrorStatusCodes;
import com.vta.vtabackend.exceptions.VTAException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository _userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> (Users) _userRepository.findByEmail(email)
                .orElseThrow(() -> new VTAException(VTAException.Type.NOT_FOUND,
                        ErrorStatusCodes.USER_NOT_FOUND.getMessage(),
                        ErrorStatusCodes.USER_NOT_FOUND.getCode()));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
