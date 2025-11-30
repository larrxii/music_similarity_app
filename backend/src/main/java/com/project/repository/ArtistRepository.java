// backend/src/main/java/com/musicsimilarity/repository/ArtistRepository.java
package com.project.repository;

import com.project.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findBySpotifyId(String spotifyId);

    List<Artist> findByNameContainingIgnoreCase(String name);

    boolean existsBySpotifyId(String spotifyId); // Добавили для проверки существования
}