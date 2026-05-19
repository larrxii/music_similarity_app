package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.TrackFeature;
import com.project.service.TrackRecommendationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TrackController {
    @Autowired
    private TrackRecommendationService service;

    @GetMapping("/recommend/tracks/{trackName}")
    public List<TrackFeature> recommendTracks(@PathVariable String trackName) {
        return service.recommendTracks(trackName);
    }
}