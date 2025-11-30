package com.project.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "✅ Music Similarity App is running with PostgreSQL!";
    }

    @GetMapping("/test")
    public String test() {
        return "🎵 Spring Boot + PostgreSQL + Spotify API = Working!";
    }
}