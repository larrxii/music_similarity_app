package com.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Service
@Slf4j
public class SpotifyAuthService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private String cachedToken;

    public SpotifyAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        if (cachedToken != null) {
            return cachedToken;
        }
        return requestNewToken();
    }

    private String requestNewToken() {
        try {
            log.info("Requesting new Spotify access token...");

            String credentials = clientId + ":" + clientSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedCredentials);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            // Создаем временный класс для парсинга ответа
            var response = restTemplate.postForEntity(
                    "https://accounts.spotify.com/api/token",
                    request,
                    TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                cachedToken = response.getBody().access_token;
                log.info("Successfully obtained Spotify access token");
                return cachedToken;
            }

        } catch (Exception e) {
            log.error("Error getting Spotify access token: {}", e.getMessage());
        }

        return null;
    }

    // Временный класс для парсинга ответа
    private static class TokenResponse {
        public String access_token;
        public String token_type;
        public Integer expires_in;
    }
}