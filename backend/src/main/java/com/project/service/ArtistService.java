package com.project.service;

import com.project.dto.ArtistDTO;
import com.project.dto.SpotifyArtistResponse;
import com.project.entity.Artist;
import com.project.repository.ArtistRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SpotifyClient spotifyClient;

    public ArtistService(ArtistRepository artistRepository, SpotifyClient spotifyClient) {
        this.artistRepository = artistRepository;
        this.spotifyClient = spotifyClient;
    }

    public List<ArtistDTO> searchArtists(String query, int limit) {
        log.info("Searching artists for query: '{}' with limit: {}", query, limit);

        try {
            // 1. Пытаемся получить данные из Spotify
            Map<String, Object> spotifyResponse = spotifyClient.searchArtists(query, limit);

            if (spotifyResponse != null && spotifyResponse.containsKey("artists")) {
                Map<String, Object> artistsData = (Map<String, Object>) spotifyResponse.get("artists");
                List<Map<String, Object>> items = (List<Map<String, Object>>) artistsData.get("items");

                if (items != null && !items.isEmpty()) {
                    log.info("Successfully retrieved {} artists from Spotify", items.size());

                    // Конвертируем и сохраняем артистов
                    List<Artist> artists = items.stream()
                            .map(this::convertSpotifyItemToArtist)
                            .collect(Collectors.toList());

                    // Сохраняем в БД (Spring Data автоматически пропустит дубликаты по spotifyId)
                    List<Artist> savedArtists = artistRepository.saveAll(artists);
                    log.info("Saved {} artists to database", savedArtists.size());

                    // Конвертируем в DTO и возвращаем
                    return savedArtists.stream()
                            .map(this::convertToDTO)
                            .limit(limit)
                            .collect(Collectors.toList());
                }
            }

        } catch (Exception e) {
            log.error("Error searching artists: {}", e.getMessage());

            // Fallback: поиск в локальной БД
            List<Artist> localArtists = artistRepository.findByNameContainingIgnoreCase(query);
            log.info("Using local DB fallback, found {} artists", localArtists.size());

            return localArtists.stream()
                    .map(this::convertToDTO)
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        log.warn("No artists found for query: {}", query);
        return List.of();
    }

    // ДОБАВЛЯЕМ НЕДОСТАЮЩИЙ МЕТОД
    public Optional<ArtistDTO> getArtistBySpotifyId(String spotifyId) {
        log.info("Searching for artist with Spotify ID: {}", spotifyId);
        return artistRepository.findBySpotifyId(spotifyId)
                .map(this::convertToDTO);
    }

    public Optional<ArtistDTO> getArtistById(Long id) {
        log.info("Searching for artist with ID: {}", id);
        return artistRepository.findById(id)
                .map(this::convertToDTO);
    }

    private Artist convertSpotifyItemToArtist(Map<String, Object> item) {
        Artist artist = new Artist();
        artist.setSpotifyId((String) item.get("id"));
        artist.setName((String) item.get("name"));
        artist.setPopularity((Integer) item.get("popularity"));

        // Обрабатываем жанры
        List<String> genres = (List<String>) item.get("genres");
        artist.setGenres(genres != null && !genres.isEmpty() ? genres : List.of("Unknown"));

        // Обрабатываем изображения
        List<Map<String, Object>> images = (List<Map<String, Object>>) item.get("images");
        if (images != null && !images.isEmpty()) {
            artist.setImageUrl((String) images.get(0).get("url"));
        }

        return artist;
    }

    private ArtistDTO convertToDTO(Artist artist) {
        ArtistDTO dto = new ArtistDTO();
        dto.setId(artist.getId());
        dto.setSpotifyId(artist.getSpotifyId());
        dto.setName(artist.getName());
        // dto.setGenres(artist.getGenres());
        dto.setImageUrl(artist.getImageUrl());
        dto.setPopularity(artist.getPopularity());
        return dto;
    }
}