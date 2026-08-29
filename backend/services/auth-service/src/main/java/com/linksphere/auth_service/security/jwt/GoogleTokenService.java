package com.linksphere.auth_service.security.jwt;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleTokenService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleIdToken.Payload verify(String idToken) {

        try {

            GoogleIdToken googleIdToken = verifier.verify(idToken);

            if (googleIdToken == null) {
                throw new IllegalArgumentException(
                        "Invalid Google ID token");
            }

            return googleIdToken.getPayload();

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Google authentication failed",
                    e);
        }
    }
}
