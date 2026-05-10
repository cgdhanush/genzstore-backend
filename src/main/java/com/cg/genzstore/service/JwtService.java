package com.cg.genzstore.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import com.cg.genzstore.model.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Must be at least 32 characters for HS256
    private final String SECRET = "mysecretkeymysecretkeymysecretkey12";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(User user) {
        if (user == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User or email cannot be null");
        }

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        try {
            return extractClaims(token).getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token: cannot extract email", e);
        }
    }

    public boolean isTokenValid(String token, User user) {
        try {
            if (token == null || user == null || user.getEmail() == null) {
                return false;
            }

            String email = extractEmail(token);
            return email.equals(user.getEmail()) && !isTokenExpired(token);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    private Claims extractClaims(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token cannot be null or empty");
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return jws.getPayload();

        } catch (JwtException e) {
            throw new RuntimeException("Failed to parse JWT token", e);
        }
    }
}