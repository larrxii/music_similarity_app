package com.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

@Service
@Slf4j
public class SpotifyClient {

    private final SpotifyAuthService authService;
    private final RestTemplate restTemplate;

    public SpotifyClient(SpotifyAuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> searchArtists(String query, int limit) {
        try {
            String accessToken = authService.getAccessToken();
            if (accessToken == null) {
                throw new RuntimeException("Failed to get Spotify access token");
            }

            String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
                    .queryParam("q", query)
                    .queryParam("type", "artist")
                    .queryParam("limit", limit)
                    .toUriString();

            log.info("Searching Spotify for: {}", query);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Successfully found artists on Spotify for query: {}", query);
                return response.getBody();
            }

        } catch (Exception e) {
            log.error("Error searching artists on Spotify for query '{}': {}", query, e.getMessage());
        }

        return null;
    }
}