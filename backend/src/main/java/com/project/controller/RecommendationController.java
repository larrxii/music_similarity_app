package com.project.controller;

import com.project.entity.Artist;
import com.project.service.SimilarityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@CrossOrigin
public class RecommendationController {

    private final SimilarityService similarityService;

    public RecommendationController(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    @GetMapping("/{artistName}")
    public List<Artist> recommend(@PathVariable String artistName) {
        return similarityService.findSimilarArtists(artistName);
    }
}