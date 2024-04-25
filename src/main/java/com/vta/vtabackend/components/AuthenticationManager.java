package com.vta.vtabackend.components;

import com.vta.vtabackend.exceptions.ApiException;
import com.vta.vtabackend.exceptions.CustomException;
import com.vta.vtabackend.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        try {
            if (!jwtUtil.validateToken(authToken)) {
                return Mono.error(new CustomException("Token is expired or invalid."));
            }
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            List<GrantedAuthority> authorities = new ArrayList<>();
            // Assuming role is a single role. If it's multiple roles, you would need to split and add each one.
            authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role", String.class)));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, authorities);

            return Mono.just(auth);

        } catch (ExpiredJwtException e) {
            return Mono.error(new CustomException("Token is expired. Please log in again."));
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            return Mono.error(new CustomException("Token is invalid. Please log in again."));
        } catch (Exception e) {
            return Mono.error(new ApiException("An internal error occurred while trying to authenticate."));
        }
    }
}