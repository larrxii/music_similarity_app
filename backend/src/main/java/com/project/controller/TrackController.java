package com.project.controller;

import com.project.dto.TrackDataDTO;
import com.project.entity.TrackFeature;
import com.project.service.FeatureExtractionService;
import com.project.service.TrackRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TrackController {

    private final TrackRecommendationService recommendationService;
    private final FeatureExtractionService featureExtractionService;

    // === Существующий метод ===
    @GetMapping("/recommend/tracks/{trackName}")
    public List<TrackFeature> recommendTracks(@PathVariable String trackName) {
        return recommendationService.recommendTracks(trackName);
    }

    // === Новый метод — добавление трека ===
    @PostMapping("/tracks")
    public ResponseEntity<?> addTrack(@RequestBody TrackDataDTO dto) {
        try {
            TrackFeature trackFeature = convertToEntity(dto);

            // Генерируем embedding
            float[] embeddingArray = featureExtractionService.buildEmbedding(trackFeature);
            trackFeature.setEmbedding(featureExtractionService.toVector(embeddingArray).toString());

            TrackFeature saved = recommendationService.saveTrack(trackFeature);

            return ResponseEntity.ok().body(Map.of(
                "message", "Track added successfully",
                "trackId", saved.getTrackId(),
                "id", saved.getId()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }

    // === Вспомогательный метод конвертации ===
    private TrackFeature convertToEntity(TrackDataDTO dto) {
        TrackFeature t = new TrackFeature();
        t.setTrackId(dto.getTrack_id());
        t.setTrackName(dto.getTrack_name());
        t.setArtistId(dto.getArtist_name());           // можно хранить как строку
        t.setAlbumName(dto.getAlbum_name());

        t.setDanceability(parseDouble(dto.getDanceability()));
        t.setEnergy(parseDouble(dto.getEnergy()));
        t.setAcousticness(parseDouble(dto.getAcousticness()));
        t.setLiveness(parseDouble(dto.getLiveness()));
        t.setSpeechiness(parseDouble(dto.getSpeechiness()));
        t.setValence(parseDouble(dto.getValence()));
        t.setTempo(parseDouble(dto.getTempo()));

        // popularity, year и т.д. можно добавить в сущность при необходимости
        return t;
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}