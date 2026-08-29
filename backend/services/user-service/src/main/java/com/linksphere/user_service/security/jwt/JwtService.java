package com.linksphere.user_service.security.jwt;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public UUID extractUserId(String token) {

        String userId = extractAllClaims(token)
                .get("uid", String.class);

        return UUID.fromString(userId);
    }

    public boolean isTokenExpired(String token) {

        Date expiration =
                extractAllClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token) {

        try {

            Claims claims = extractAllClaims(token);

            if (!jwtProperties.getIssuer().equals(
                    claims.getIssuer())) {
                return false;
            }

            return claims.getExpiration()
                    .after(new Date());

        } catch (Exception e) {

            return false;
        }
    }
}