package com.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "artists")
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String spotifyId;

    private String name;

    @ElementCollection
    private List<String> genres; // Изменили: теперь список жанров

    private String imageUrl;
    private Integer popularity;

    // Пока уберём сложные поля для MVP
    // private String country;
    // private Double[] featureVector;
    // private List<String> topTracks;
}