package com.project.dto;

import lombok.Data;

@Data
public class TrackDataDTO {
    private String track_id;
    private String track_name;
    private String artist_name;
    private String album_name;
    private String year;
    private String popularity;
    private String artwork_url;
    private String acousticness;
    private String danceability;
    private String energy;
    private String liveness;
    private String speechiness;
    private String valence;
    private String tempo;
    private String loudness;
    private String key;
    private String mode;
    private String time_signature;
    private String track_url;
    private String language;
}