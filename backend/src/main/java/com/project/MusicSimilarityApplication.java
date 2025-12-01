package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicSimilarityApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicSimilarityApplication.class, args);
        System.out.println("🎵 Application started at http://localhost:8080");
    }
}