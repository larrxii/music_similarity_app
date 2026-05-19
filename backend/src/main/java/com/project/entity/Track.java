package com.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tracks")
@Data
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String artistName;

    private String spotifyId;

    private String imageUrl;

    private Float popularity;

    @Column(columnDefinition = "TEXT")
    private String featureVector;

    // getters/setters
}