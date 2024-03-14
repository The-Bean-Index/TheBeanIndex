package com.example.beanIndex;

import com.example.beanIndex.models.AuthRecord;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GoogleAuth {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "client_secret.json";


    public AuthRecord signIn() throws GeneralSecurityException, IOException {
        final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
            new InputStreamReader(Objects.requireNonNull(GoogleAuth.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH))));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Arrays.asList("openid", "email", "profile"))
            .setAccessType("offline")
            .build();

        String url = flow.newAuthorizationUrl()
            .setRedirectUri("urn:ietf:wg:oauth:2.0:oob")
            .setScopes(Arrays.asList("openid", "email", "profile"))
            .build();

        System.out.println("Please open the following URL in your browser:\n" + url);
        System.out.println("Enter the authorization code:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();

        GoogleTokenResponse response = flow.newTokenRequest(code)
            .setRedirectUri("urn:ietf:wg:oauth:2.0:oob")
            .execute();

        String idToken = response.getIdToken();
        Long expiration = response.getExpiresInSeconds();
        Date expirationDate = new Date(new Date().getTime() + (expiration - 60) * 1000);

        return new AuthRecord(idToken, expirationDate);
    }
}
