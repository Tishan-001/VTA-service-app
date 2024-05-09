package com.vta.vtabackend.utils;

import com.vta.vtabackend.documents.TourGuide;
import com.vta.vtabackend.documents.Transport;
import com.vta.vtabackend.documents.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // If needed, handle the expired case specifically
        }
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Token is expired
        }
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return doGenerateToken(claims, user.getId());
    }

    // Generate Token for Micro services
    public String generateTokenForTransport(Transport transport){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",transport.getRole());
        return doGenerateToken(claims, transport.getId());
    }
    public String generateTokenForTourgide(TourGuide tourGuide){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",tourGuide.getRole());
        return doGenerateToken(claims, tourGuide.getId());
    }
    private String doGenerateToken(Map<String, Object> claims, String userId) {
        long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    public String getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject(); // The subject was set to userId in doGenerateToken
    }

}