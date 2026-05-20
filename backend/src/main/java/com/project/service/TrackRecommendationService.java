package com.project.service;

import com.project.entity.TrackFeature;
import com.project.repository.TrackFeatureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackRecommendationService {

    @Autowired
    private TrackFeatureRepository repository;

    public List<TrackFeature> recommendTracks(String trackName) {

        List<TrackFeature> tracks =
                repository.findByTrackNameContainingIgnoreCase(trackName);

        if (tracks.isEmpty()) {
            return List.of();
        }

        TrackFeature target = tracks.get(0);

        float[] targetVector =
                parseEmbedding(target.getEmbedding());

        return repository.findAll().stream()

                .filter(t -> !t.getId().equals(target.getId()))

                .filter(t -> t.getEmbedding() != null)

                .sorted((a, b) -> Double.compare(

                        similarity(
                                parseEmbedding(b.getEmbedding()),
                                targetVector
                        ),

                        similarity(
                                parseEmbedding(a.getEmbedding()),
                                targetVector
                        )
                ))

                .limit(5)

                .toList();
    }

    public TrackFeature saveTrack(TrackFeature trackFeature) {
        return repository.save(trackFeature);
    }

    private float[] parseEmbedding(String embedding) {

        embedding = embedding
                .replace("[", "")
                .replace("]", "");

        String[] parts = embedding.split(",");

        float[] vector = new float[parts.length];

        for (int i = 0; i < parts.length; i++) {
            vector[i] = Float.parseFloat(parts[i]);
        }

        return vector;
    }

    private double similarity(float[] a, float[] b) {
        double dot = 0.0;

        double normA = 0.0;
        double normB = 0.0;

        double euclideanSum = 0.0;

        for (int i = 0; i < a.length; i++) {

            // cosine similarity
            dot += a[i] * b[i];

            normA += a[i] * a[i];
            normB += b[i] * b[i];

            // euclidean distance
            euclideanSum += Math.pow(a[i] - b[i], 2);
        }

        // cosine
        double cosine =
                dot / (Math.sqrt(normA) * Math.sqrt(normB));

        // euclidean distance
        double euclideanDistance =
                Math.sqrt(euclideanSum);

        // convert distance -> similarity
        double euclideanScore =
                1.0 / (1.0 + euclideanDistance);

        // hybrid similarity
        double alpha = 0.7;

        return alpha * cosine + (1 - alpha) * euclideanScore;
    }
}