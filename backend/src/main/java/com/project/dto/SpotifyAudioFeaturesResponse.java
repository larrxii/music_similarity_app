package com.project.dto;

import lombok.Data;
import java.util.List;

@Data
public class SpotifyAudioFeaturesResponse {
    private Double danceability;
    private Double energy;
    private Double acousticness;
    private Double valence;
    private Double tempo;
    private Double loudness;
}