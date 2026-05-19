package com.project.repository;

import com.project.entity.TrackFeature;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackFeatureRepository extends JpaRepository<TrackFeature, Long> {

    List<TrackFeature> findByTrackNameContainingIgnoreCase(
            String trackName
    );
}