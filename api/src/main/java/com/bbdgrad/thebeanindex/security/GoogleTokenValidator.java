package com.bbdgrad.thebeanindex.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
public class GoogleTokenValidator {

    private static final String CLIENT_ID = "926482625868-6igj3asusl8am389agqpp115b8oj9au5.apps.googleusercontent.com";


    public boolean isTokenValid(String idTokenString) {
        try {
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

            GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, idTokenString);
            boolean verified = verifier.verify(idToken);

            if (verified) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String audience = payload.getAudience().toString();

                log.atInfo().log("Request from {}", payload.getEmail());

                return audience.equals(CLIENT_ID);
            }

            return false;
        } catch (GeneralSecurityException | IOException e) {
            log.atError().log("Error validating token");
            return false;
        }
    }
}
