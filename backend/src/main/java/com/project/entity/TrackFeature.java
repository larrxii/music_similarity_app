package com.project.entity;

import com.pgvector.PGvector;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "track_features")
@Data
public class TrackFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackId;

    @Column(columnDefinition = "TEXT")
    private String artistId;

    @Column(columnDefinition = "TEXT")
    private String trackName;

    @Column(columnDefinition = "TEXT")
    private String albumName;

    private Double danceability;
    private Double energy;
    private Double acousticness;
    private Double instrumentalness;
    private Double liveness;
    private Double speechiness;
    private Double valence;
    private Double tempo;

    @Column(name = "embedding", columnDefinition = "TEXT")
    private String embedding;
}