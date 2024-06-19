package com.vta.vtabackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/auth/register/email",
            "/auth/register/email/verify",
            "/auth/getcode",
            "/auth/login/email",
            "/auth/login/admin",
            "/auth/count",
            "/hotels/create",
            "/hotels/",
            "/hotels/update",
            "/hotels/delete",
            "/hotels/get",
            "/hotels/count",
            "/tourguides/register",
            "/tourguides/",
            "/tourguides/tourguide/{id}",
            "/tourguides/update",
            "/tourguides/delete",
            "/tourguides/count",
            "/tourguides/guider/{email}",
            "/tourpackage/create",
            "/tourpackage/",
            "/tourpackage/{id}",
            "/transports/",
            "/transports/create",
            "/transports/transport",
            "/images/upload",
            "/booking/create",
            "/hotel-booking/create",
            "/hotel-booking/get-bookings",
            "/tour-guide-booking/create",
            "/tour-guide-booking/get-bookings",
            "/transport-booking/create",
            "/transport-booking/get-bookings",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(withDefaults());  // CORS configuration

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // Allow cookies and other credentials
        config.addAllowedOrigin("http://localhost:3000");  // Allow specific origin
        config.addAllowedHeader("*");  // Allow all headers
        config.addAllowedMethod("*");  // Allow all HTTP methods
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}