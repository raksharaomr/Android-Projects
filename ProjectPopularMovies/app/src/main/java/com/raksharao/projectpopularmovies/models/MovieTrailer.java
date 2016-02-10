package com.raksharao.projectpopularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by raksharaomr on 2/2/16.
 */
public class MovieTrailer {
    private int id;
    private List<Result> results;

    public int getId() {
        return id;
    }

    public MovieTrailer setId(int id) {
        this.id = id;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public MovieTrailer setResults(List<Result> results) {
        this.results = results;
        return this;
    }

    public static class Result {
        String id;
        @SerializedName("iso_639_1")
        String encoding;
        String key;
        @SerializedName("name")
        String videoName;
        String site;
        String size;
        String type;

        public String getId() {
            return id;
        }

        public Result setId(String id) {
            this.id = id;
            return this;
        }

        public String getEncoding() {
            return encoding;
        }

        public Result setEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public String getKey() {
            return key;
        }

        public Result setKey(String key) {
            this.key = key;
            return this;
        }

        public String getVideoName() {
            return videoName;
        }

        public Result setVideoName(String videoName) {
            this.videoName = videoName;
            return this;
        }

        public String getSite() {
            return site;
        }

        public Result setSite(String site) {
            this.site = site;
            return this;
        }

        public String getSize() {
            return size;
        }

        public Result setSize(String size) {
            this.size = size;
            return this;
        }

        public String getType() {
            return type;
        }

        public Result setType(String type) {
            this.type = type;
            return this;
        }
    }
}
