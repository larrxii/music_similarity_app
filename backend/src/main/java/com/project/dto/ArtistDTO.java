// backend/src/main/java/com/musicsimilarity/dto/ArtistDTO.java
package com.project.dto;

import lombok.Data;

@Data
public class ArtistDTO {
    private Long id;
    private String spotifyId;
    private String name;
    private String genre;
    private String imageUrl;
    private Integer popularity;
}