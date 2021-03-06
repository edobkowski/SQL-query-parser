package com.codecool.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleAuthorizeUtil {

    private GoogleAuthorizeUtil() {
        
    }

    private static Credential credential;

    public static Credential authorize() throws IOException, GeneralSecurityException {
        if(credential == null) {
            InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/credentials.json");
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
            List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes).setDataStoreFactory(new MemoryDataStoreFactory())
                    .setAccessType("offline").build();
            credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("72994257151-t0mbjqlok3n05f62teeno55vvjt5at6f.apps.googleusercontent.com");
        }

        return credential;
    }
}
