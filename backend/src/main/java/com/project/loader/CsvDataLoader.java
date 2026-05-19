// package com.project.loader;

// import com.project.entity.TrackFeature;
// import com.project.repository.TrackFeatureRepository;
// import com.project.service.FeatureExtractionService;

// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVRecord;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.Reader;

// // @Component
// // @Order(1)
// public class CsvDataLoader implements CommandLineRunner {

//     private final TrackFeatureRepository trackRepo;
//     private final FeatureExtractionService featureService;

//     public CsvDataLoader(
//             TrackFeatureRepository trackRepo,
//             FeatureExtractionService featureService
//     ) {
//         this.trackRepo = trackRepo;
//         this.featureService = featureService;
//     }

//     @Override
//     public void run(String... args) throws Exception {

//         if (trackRepo.count() > 0) {
//             System.out.println("Track features already loaded");
//             return;
//         }

//         InputStream is = getClass().getResourceAsStream("/dataset.csv");

//         if (is == null) {
//             throw new RuntimeException("dataset.csv not found");
//         }

//         Reader reader = new InputStreamReader(is);

//         CSVFormat format = CSVFormat.DEFAULT.builder()
//                 .setHeader(
//                         "index",
//                         "track_id",
//                         "artists",
//                         "album_name",
//                         "track_name",
//                         "popularity",
//                         "duration_ms",
//                         "explicit",
//                         "danceability",
//                         "energy",
//                         "key",
//                         "loudness",
//                         "mode",
//                         "speechiness",
//                         "acousticness",
//                         "instrumentalness",
//                         "liveness",
//                         "valence",
//                         "tempo",
//                         "time_signature",
//                         "track_genre"
//                 )
//                 .setSkipHeaderRecord(true)
//                 .setTrim(true)
//                 .build();

//         Iterable<CSVRecord> records = format.parse(reader);

//         int loaded = 0;

//         for (CSVRecord p : records) {

//             try {

//                 TrackFeature t = new TrackFeature();

//                 t.setTrackId(p.get("track_id"));
//                 t.setTrackName(p.get("track_name"));
//                 t.setAlbumName(p.get("album_name"));
//                 t.setArtistId(p.get("artists"));

//                 t.setDanceability(parseDouble(p.get("danceability")));
//                 t.setEnergy(parseDouble(p.get("energy")));
//                 t.setAcousticness(parseDouble(p.get("acousticness")));
//                 t.setInstrumentalness(parseDouble(p.get("instrumentalness")));
//                 t.setLiveness(parseDouble(p.get("liveness")));
//                 t.setSpeechiness(parseDouble(p.get("speechiness")));
//                 t.setValence(parseDouble(p.get("valence")));
//                 t.setTempo(parseDouble(p.get("tempo")));

//                 float[] embedding = featureService.buildEmbedding(t);

//                 t.setEmbedding(
//                     featureService.toVector(embedding)
//                 );

//                 trackRepo.save(t);

//                 loaded++;

//                 if (loaded % 1000 == 0) {
//                     System.out.println("Loaded tracks: " + loaded);
//                 }

//             } catch (Exception e) {

//                 System.err.println(
//                         "Skip bad row: "
//                                 + e.getMessage()
//                                 + " row="
//                                 + p.getRecordNumber()
//                 );
//             }
//         }

//         System.out.println("CSV loaded into DB");
//         System.out.println("Total tracks loaded: " + loaded);
//     }

//     private Double parseDouble(String v) {

//         try {
//             return Double.parseDouble(v);
//         } catch (Exception e) {
//             return 0.0;
//         }
//     }
// }