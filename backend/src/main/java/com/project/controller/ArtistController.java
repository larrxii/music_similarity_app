package com.project.controller;

import com.project.dto.ArtistDTO;
import com.project.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "*") // Для разработки - разрешаем все origins
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistDTO>> searchArtists(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int limit) {

        List<ArtistDTO> artists = artistService.searchArtists(query, limit);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long id) {
        return artistService.getArtistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<ArtistDTO> getArtistBySpotifyId(@PathVariable String spotifyId) {
        return artistService.getArtistBySpotifyId(spotifyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<ArtistDTO>> getSimilarArtists(
            @PathVariable Long id,
            @RequestParam(defaultValue = "8") int limit) {
        // TODO: Реализовать логику поиска похожих артистов
        return ResponseEntity.ok(List.of());
    }
}