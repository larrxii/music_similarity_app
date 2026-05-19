package com.project.service;

import com.project.entity.Artist;
import com.project.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarityService {

    private final ArtistRepository artistRepository;

    public SimilarityService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findSimilarArtists(String artistName) {

        Artist artist = artistRepository
                .findByNameIgnoreCase(artistName)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        return artistRepository.findSimilar(
                artist.getFeatureVector()
        );
    }
}