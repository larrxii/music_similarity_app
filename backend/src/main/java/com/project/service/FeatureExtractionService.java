package com.project.service;
import java.util.List;

import com.project.entity.Artist;
import com.project.entity.TrackFeature;
import com.pgvector.PGvector;
import com.project.dto.SpotifyAudioFeaturesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.pgvector.PGvector;

@Service
@Slf4j
public class FeatureExtractionService {
    private final SpotifyClient spotifyClient;

    public FeatureExtractionService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public float[] buildEmbedding(TrackFeature t) {

        return new float[] {
            normalize(t.getDanceability()),
            normalize(t.getEnergy()),
            normalize(t.getAcousticness()),
            normalize(t.getInstrumentalness()),
            normalize(t.getLiveness()),
            normalize(t.getSpeechiness()),
            normalize(t.getValence()),
            normalizeTempo(t.getTempo())
        };
    }

    private float normalize(Double v) {
        return v == null ? 0f : v.floatValue();
    }

    private float normalizeTempo(Double tempo) {
        return tempo == null ? 0f : (float)(tempo / 200.0);
    }

    public PGvector toVector(float[] embedding) {
        return new PGvector(embedding);
    }

    public String generateArtistEmbedding(String spotifyArtistId) {

        List<String> trackIds =
                spotifyClient.getArtistTopTrackIds(spotifyArtistId);

        if (trackIds.isEmpty()) {
            return null;
        }

        List<SpotifyAudioFeaturesResponse> features = new ArrayList<>();

        for (String trackId : trackIds) {

            SpotifyAudioFeaturesResponse audioFeatures =
                    spotifyClient.getAudioFeatures(trackId);

            if (audioFeatures != null) {
                features.add(audioFeatures);
            }
        }

        if (features.isEmpty()) {
            return null;
        }

        double avgDanceability =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getDanceability)
                        .average()
                        .orElse(0.0);

        double avgEnergy =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getEnergy)
                        .average()
                        .orElse(0.0);

        double avgAcousticness =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getAcousticness)
                        .average()
                        .orElse(0.0);

        double avgValence =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getValence)
                        .average()
                        .orElse(0.0);

        double avgTempo =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getTempo)
                        .average()
                        .orElse(0.0);

        double avgLoudness =
                features.stream()
                        .mapToDouble(SpotifyAudioFeaturesResponse::getLoudness)
                        .average()
                        .orElse(0.0);

        return "[" +
                avgDanceability + "," +
                avgEnergy + "," +
                avgAcousticness + "," +
                avgValence + "," +
                avgTempo + "," +
                avgLoudness +
                "]";
    }
}