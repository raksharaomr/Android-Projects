package com.raksharao.projectpopularmovies.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raksharaomr on 9/16/15.
 */
public class MovieDetail implements Parcelable {

    private static final String KEY_ORIGINAL_TITLE = "originalTitle";
    private static final String KEY_POSTER_PATH = "posterPath";
    private static final String KEY_RELEASE_DATE = "relDate";
    private static final String KEY_USER_RATING = "userRating";
    private static final String KEY_PLOT_SYNOPSIS = "plotSynopsis";
    private static final String KEY_DURATION = "duration";

    private String originalTitle;
    private String posterPath;
    private String relDate;
    private String userRating;
    private String plotSynopsis;
    private String duration;

    public MovieDetail() {

    }

    public MovieDetail(
        String originalTitle,
        String posterPath,
        String relDate,
        String userRating,
        String plotSynopsis,
        String duration
    ) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.relDate = relDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
        this.duration = duration;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public MovieDetail setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public MovieDetail setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getRelDate() {
        return relDate;
    }
    public MovieDetail setRelDate(String relDate) {
        this.relDate = relDate;
        return this;
    }

    public String getUserRating() {
        return userRating;
    }
    public MovieDetail setUserRating(String userRating) {
        this.userRating = userRating;
        return this;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }
    public MovieDetail setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
        return this;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putString(KEY_DURATION, getDuration());
        bundle.putString(KEY_ORIGINAL_TITLE, getOriginalTitle());
        bundle.putString(KEY_PLOT_SYNOPSIS, getPlotSynopsis());
        bundle.putString(KEY_RELEASE_DATE, getRelDate());
        bundle.putString(KEY_USER_RATING, getUserRating());
        bundle.putString(KEY_POSTER_PATH, getPosterPath());
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();

            return new MovieDetail(
                bundle.getString(KEY_ORIGINAL_TITLE),
                bundle.getString(KEY_POSTER_PATH),
                bundle.getString(KEY_RELEASE_DATE),
                bundle.getString(KEY_USER_RATING),
                bundle.getString(KEY_PLOT_SYNOPSIS),
                bundle.getString(KEY_DURATION)
            );
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };
}
