package com.project.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String health() {
        return "✅ Music Similarity App is running with PostgreSQL!";
    }

    @GetMapping("/api/test")
    public String test() {
        return "🎵 Spring Boot + PostgreSQL + Spotify API = Working!";
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Music Similarity API! Use /api/health, /api/test, /api/artists/search?query=...";
    }
}