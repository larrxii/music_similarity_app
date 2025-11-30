package com.project.dto;

import lombok.Data;
import java.util.List;

@Data
public class SpotifyArtistResponse {
    private Artists artists;

    @Data
    public static class Artists {
        private List<Item> items;
    }

    @Data
    public static class Item {
        private String id;
        private String name;
        private List<String> genres;
        private List<Image> images;
        private Integer popularity;

        @Data
        public static class Image {
            private String url;
            private Integer height;
            private Integer width;
        }
    }
}