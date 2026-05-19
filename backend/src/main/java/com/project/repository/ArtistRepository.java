// backend/src/main/java/com/musicsimilarity/repository/ArtistRepository.java
package com.project.repository;

import com.project.entity.Artist;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByNameIgnoreCase(String name);

    List<Artist> findByNameContainingIgnoreCase(String name);

    Optional<Artist> findBySpotifyId(String spotifyId);

    @Query(value = """
        SELECT *
        FROM artists
        ORDER BY feature_vector <-> CAST(:vector AS vector)
        LIMIT 5
    """, nativeQuery = true)
    List<Artist> findSimilar(@Param("vector") String vector);
}