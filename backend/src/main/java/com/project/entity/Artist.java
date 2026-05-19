package com.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "artists")
@Data
public class Artist {

    @Id
    private Long id;

    private String name;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer popularity;

    @Column(name = "feature_vector", columnDefinition = "vector(8)")
    private String featureVector;
}

// @Entity
// @Table(name = "artists")
// @Data
// public class Artist {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true)
//     private String spotifyId;

//     private String name;

//     @ElementCollection
//     private List<String> genres;

//     private String imageUrl;

//     private Integer popularity;

//     @Column(columnDefinition = "TEXT")
//     private String featureVector;
// }