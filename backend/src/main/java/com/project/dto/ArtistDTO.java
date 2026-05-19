// backend/src/main/java/com/musicsimilarity/dto/ArtistDTO.java
package com.project.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class ArtistDTO {

    private Long id;
    private String spotifyId;
    private String name;
    private String genre;
    private Integer popularity;
    private Double similarity;
    private List<String> genres;
}