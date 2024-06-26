package com.vta.vtabackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            "/auth/forgot/password",
            "/auth/forgot/reset/password",
            "/hotels/create",
            "/hotels/",
            "/hotels/update",
            "/hotels/delete/{id}",
            "/hotels/get",
            "/hotels/count",
            "/hotels/add/room",
            "/hotels/get/rooms",
            "/hotels/get/filter/hotel",
            "/hotels/delete/room/{id}",
            "/hotels/get/room/{id}",
            "/tourguides/register",
            "/tourguides/",
            "/tourguides/tourguide/{id}",
            "/tourguides/update",
            "/tourguides/delete",
            "/tourguides/count",
            "/tourguides/guider",
            "/tourpackage/create",
            "/tourpackage/",
            "/tourpackage/{id}",
            "/transports/",
            "/transports/create",
            "/transports/transport",
            "/transports/count",
            "/transports/transport-id/{id}",
            "/vehicle/",
            "/vehicle/create",
            "/vehicle/by-transport",
            "/vehicle/add",
            "/vehicle/update",
            "/vehicle/delete",
            "/vehicle/available",
            "/images/upload",
            "/booking/create",
            "/hotel-booking/create",
            "/hotel-booking/get-bookings",
            "/tour-guide-booking/create",
            "/tour-guide-booking/get-bookings",
            "/transport-booking/create",
            "/transport-booking/get-bookings-service",
            "/transport-booking/get-bookings-user",

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
        config.addAllowedOrigin("https://vtasrilankaweb.netlify.app");  // Allow specific origin
        config.addAllowedOriginPattern("https://vtarilankaweb.vercel.app");
        config.addAllowedHeader("*");  // Allow all headers
        config.addAllowedMethod("*");  // Allow all HTTP methods
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
