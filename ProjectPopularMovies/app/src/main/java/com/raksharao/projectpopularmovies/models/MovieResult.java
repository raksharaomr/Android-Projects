package com.raksharao.projectpopularmovies.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by raksharaomr on 7/31/15.
 */
public class MovieResult implements Parcelable {

    private static String KEY_ID = "id";
    private static String KEY_POSTER_PATH = "posterPath";

    @SerializedName("page")
    int pageNumber;

    List<Result> results;


    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("total_results")
    int totalResults;

    Result result = new Result();

    public MovieResult() {

    }

    public MovieResult(int id, String posterPath) {
        this.result.id = id;
        this.result.posterPath = posterPath;
    }

    public  String getPosterPath() {
        return this.result.getPosterPath();
    }
    public int getPageNumber() {
        return pageNumber;
    }

    public MovieResult setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public MovieResult setResults(List<Result> results) {
        this.results = results;
        return this;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putString(KEY_POSTER_PATH, result.getPosterPath());
        bundle.putInt(KEY_ID, result.getId());
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();

            return new Result(bundle.getInt(KEY_ID), bundle.getString(KEY_POSTER_PATH));
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public static class Result {

        boolean adult;

        @SerializedName("backdrop_path")
        String backdropPath;

        @SerializedName("genre_ids")
        List<Integer> genreIds;

        int id;

        @SerializedName("original_language")
        String originalLanguage;

        @SerializedName("original_title")
        String originalTitle;

        String overview;

        @SerializedName("release_date")
        String releaseDate;

        @SerializedName("poster_path")
        String posterPath;

        String popularity;
        String title;
        boolean video;

        @SerializedName("vote_average")
        double voteAverage;

        @SerializedName("vote_count")
        int voteCount;

        public Result() {

        }

        public Result(int id, String posterPath) {
            this.id = id;
            this.posterPath = posterPath;
        }

        public boolean isAdult() {
            return adult;
        }

        public Result setAdult(boolean adult) {
            adult = adult;
            return this;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public Result setBackdropPath(String backdropPath) {
            backdropPath = backdropPath;
            return this;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public Result setGenreIds(List<Integer> genreIds) {
            genreIds = genreIds;
            return this;
        }

        public int getId() {
            return id;
        }

        public Result setId(int id) {
            id = id;
            return this;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public Result setOriginalLanguage(String originalLanguage) {
            originalLanguage = originalLanguage;
            return this;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public Result setOriginalTitle(String originalTitle) {
            originalTitle = originalTitle;
            return this;
        }

        public String getOverview() {
            return overview;
        }

        public Result setOverview(String overview) {
            overview = overview;
            return this;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public Result setReleaseDate(String releaseDate) {
            releaseDate = releaseDate;
            return this;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public Result setPosterPath(String posterPath) {
            posterPath = posterPath;
            return this;
        }

        public String getPopularity() {
            return popularity;
        }

        public Result setPopularity(String popularity) {
            popularity = popularity;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Result setTitle(String title) {
            title = title;
            return this;
        }

        public boolean isVideo() {
            return video;
        }

        public Result setVideo(boolean video) {
            video = video;
            return this;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public Result setVoteAverage(double voteAverage) {
            voteAverage = voteAverage;
            return this;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public Result setVoteCount(int voteCount) {
            voteCount = voteCount;
            return this;
        }
    }
}
