package com.vta.app.components;

import com.cloudimpl.error.core.ErrorBuilder;
import com.vta.app.error.ApiException;
import com.vta.app.utils.JWTUtil;
import io.jsonwebtoken.Claims;
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
                return Mono.error(() -> ApiException.TOKEN_EXPIRED(ErrorBuilder::build));
            }
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            String role = claims.get("role", String.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, authorities);
            return Mono.just(authenticationToken);
        } catch (Exception e) {
            return Mono.error(() -> ApiException.SOMETHING_WENT_WRONG(err -> err.wrap(e)));
        }
    }
}

