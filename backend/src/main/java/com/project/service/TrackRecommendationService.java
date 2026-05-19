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

        float[] targetVector = target.getEmbedding().toArray();

        return repository.findAll().stream()
                .filter(t -> !t.getId().equals(target.getId()))
                .sorted((a, b) -> Double.compare(
                        similarity(
                            b.getEmbedding().toArray(),
                            targetVector
                        ),
                        similarity(
                            a.getEmbedding().toArray(),
                            targetVector
                        )
                ))
                .limit(5)
                .toList();
    }

    private double similarity(float[] a, float[] b) {
        
        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        float[] a = aVec.toArray();
float[] b = bVec.toArray();

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        return dot / (
                Math.sqrt(normA) * Math.sqrt(normB)
        );
    }

    private float[] toFloatArray(Object vector) {
        if (vector instanceof double[] arr) {
            float[] result = new float[arr.length];
            for (int i = 0; i < arr.length; i++) result[i] = (float) arr[i];
            return result;
        }

        if (vector instanceof float[] arr) {
            return arr;
        }

        throw new IllegalArgumentException("Unknown vector type: " + vector);
    }
}