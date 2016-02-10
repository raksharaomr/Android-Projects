package com.raksharao.projectpopularmovies.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raksharaomr on 1/27/16.
 */
public class MovieReview implements Parcelable {
    public static String KEY_REVIEW_ID = "id";
    public static String KEY_REVIEW_RESULTS = "results";

    private int id;
    private int page;
    private ArrayList<Result> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public MovieReview() {

    }

    public MovieReview(int id, ArrayList<Result> results) {
        this.id = id;
        this.results = results;
    }


    public int getId() {
        return id;
    }

    public MovieReview setId(int id) {
        this.id = id;
        return this;
    }

    public int getPage() {
        return page;
    }

    public MovieReview setPage(int page) {
        this.page = page;
        return this;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public MovieReview setResults(ArrayList<Result> results) {
        this.results = results;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public MovieReview setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public MovieReview setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeList(results);
    }

    public static final Parcelable.Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel source) {


            return new MovieReview(source.readInt(), source.readArrayList(null));
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };


    public static class Result implements Parcelable {
        public static String KEY_REVIEW_RESULT_ID = "id";
        public static String KEY_REVIEW_RESULT_AUTHOR = "author";
        public static String KEY_REVIEW_RESULT_CONTENT = "content";
        public static String KEY_REVIEW_RESULT_URL = "url";

        String id;
        String author;
        String content;
        String url;

        public Result() {

        }

        public Result(String id, String author, String content, String url) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public Result setId(String id) {
            this.id = id;
            return this;
        }

        public String getAuthor() {
            return author;
        }

        public Result setAuthor(String author) {
            this.author = author;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Result setContent(String content) {
            this.content = content;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Result setUrl(String url) {
            this.url = url;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Bundle bundle = new Bundle();

            bundle.putString(KEY_REVIEW_RESULT_ID, getId());
            bundle.putString(KEY_REVIEW_RESULT_AUTHOR, getAuthor());
            bundle.putString(KEY_REVIEW_RESULT_CONTENT, getContent());
            bundle.putString(KEY_REVIEW_RESULT_URL, getUrl());
            dest.writeBundle(bundle);
        }

        public final Parcelable.Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel source) {
                Bundle bundle = source.readBundle();

                return new Result(
                        bundle.getString(KEY_REVIEW_RESULT_ID),
                        bundle.getString(KEY_REVIEW_RESULT_AUTHOR),
                        bundle.getString(KEY_REVIEW_RESULT_CONTENT),
                        bundle.getString(KEY_REVIEW_RESULT_URL)
                );
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }

}
