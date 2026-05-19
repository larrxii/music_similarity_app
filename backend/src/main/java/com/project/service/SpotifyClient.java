package com.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.dto.SpotifyAudioFeaturesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
public class SpotifyClient {


private static final Logger log = LoggerFactory.getLogger(SpotifyClient.class);
    
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

    public List<String> getArtistTopTrackIds(String artistId) {
        try {
            String accessToken = authService.getAccessToken();

            String url = "https://api.spotify.com/v1/artists/"
                    + artistId
                    + "/top-tracks?market=US";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            List<String> trackIds = new ArrayList<>();

            if (response.getBody() != null) {

                List<Map<String, Object>> tracks =
                        (List<Map<String, Object>>) response.getBody().get("tracks");

                for (Map<String, Object> track : tracks) {
                    trackIds.add((String) track.get("id"));
                }
            }

            return trackIds;

        } catch (Exception e) {

            log.error("Error getting top tracks: {}", e.getMessage());

            return List.of();
        }
    }

    public SpotifyAudioFeaturesResponse getAudioFeatures(String trackId) {
        try {

            String accessToken = authService.getAccessToken();

            String url =
                    "https://api.spotify.com/v1/audio-features/" + trackId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<SpotifyAudioFeaturesResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            SpotifyAudioFeaturesResponse.class
                    );

            return response.getBody();

        } catch (Exception e) {

            log.error("Error getting audio features: {}", e.getMessage());

            return null;
        }
    }
}