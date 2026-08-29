package com.linksphere.auth_service.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GoogleOAuthConfig {

    @Value("${google.oauth.client-ids}")
    private String clientIds;

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier()
            throws Exception {

        List<String> allowedClientIds = Arrays.stream(clientIds.split(","))
                .map(String::trim)
                .filter(id -> !id.isBlank())
                .toList();

        return new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(allowedClientIds)
                .build();
    }
}
