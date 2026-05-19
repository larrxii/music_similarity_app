// package com.project.loader;

// import com.pgvector.PGvector;
// import com.project.entity.Artist;
// import com.project.entity.TrackFeature;
// import com.project.repository.ArtistRepository;
// import com.project.repository.TrackFeatureRepository;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// // @Component
// // @Order(2)
// public class ArtistEmbeddingLoader implements CommandLineRunner {

//     private final TrackFeatureRepository trackRepo;
//     private final ArtistRepository artistRepo;

//     public ArtistEmbeddingLoader(
//             TrackFeatureRepository trackRepo,
//             ArtistRepository artistRepo
//     ) {
//         this.trackRepo = trackRepo;
//         this.artistRepo = artistRepo;
//     }

//     @Override
//     public void run(String... args) {

//         if (artistRepo.count() > 0) {

//             System.out.println("Artists already loaded");
//             return;
//         }

//         List<TrackFeature> tracks = trackRepo.findAll();

//         Map<String, List<float[]>> artistVectors =
//                 new HashMap<>();

//         for (TrackFeature t : tracks) {

//             if (t.getEmbedding() == null) {
//                 continue;
//             }

//             float[] parsed = t.getEmbedding().toArray();

//             artistVectors
//                     .computeIfAbsent(
//                             t.getArtistId(),
//                             k -> new ArrayList<>()
//                     )
//                     .add(parsed);
//         }

//         int loaded = 0;

//         for (Map.Entry<String, List<float[]>> entry
//                 : artistVectors.entrySet()) {

//             List<float[]> vectors = entry.getValue();

//             PGvector avgVector =
//                     average(vectors);

//             Artist artist = new Artist();

//             artist.setName(entry.getKey());

//             artist.setFeatureVector(avgVector);

//             artistRepo.save(artist);

//             loaded++;

//             if (loaded % 500 == 0) {

//                 System.out.println(
//                         "Loaded artists: " + loaded
//                 );
//             }
//         }

//         System.out.println(
//                 "Artists loaded successfully: " + loaded
//         );
//     }

//     private PGvector average(List<float[]> vectors) {

//         int dim = vectors.get(0).length;

//         float[] result = new float[dim];

//         for (float[] arr : vectors) {

//             for (int i = 0; i < dim; i++) {

//                 result[i] += arr[i];
//             }
//         }

//         for (int i = 0; i < dim; i++) {

//             result[i] /= vectors.size();
//         }

//         return new PGvector(result);
//     }
// }