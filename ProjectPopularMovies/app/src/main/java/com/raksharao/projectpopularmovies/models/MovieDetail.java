package com.raksharao.projectpopularmovies.models;

/**
 * Created by raksharaomr on 9/16/15.
 */
public class MovieDetail {
    private String originalTitle;
    private String posterPath;
    private String relDate;
    private String userRating;
    private String plotSynopsis;
    private String duration;

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
}
