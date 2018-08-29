package com.muliamaulana.cataloguemovie.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.getColumnDouble;
import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.getColumnInt;
import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.getColumnString;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_BACKDROP;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_OVERVIEW;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_POSTER;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_RELEASE_DATE;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_TITLE;
import static com.muliamaulana.cataloguemovie.provider.FavoriteColumns.COLUMN_VOTE;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class ItemResults {
    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("vote_count")
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public ItemResults(
            String overview, String originalLanguage, String originalTitle,
            boolean video, String title, List<Integer> genreIds,
            String posterPath, String backdropPath, String releaseDate,
            double voteAverage, double popularity, int id, boolean adult, int voteCount) {
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.video = video;
        this.title = title;
        this.genreIds = genreIds;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.id = id;
        this.adult = adult;
        this.voteCount = voteCount;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public ItemResults() {

    }
    public ItemResults(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, COLUMN_TITLE);
        this.backdropPath = getColumnString(cursor, COLUMN_BACKDROP);
        this.posterPath = getColumnString(cursor, COLUMN_POSTER);
        this.releaseDate = getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.voteAverage = getColumnDouble(cursor, COLUMN_VOTE);
        this.overview = getColumnString(cursor, COLUMN_OVERVIEW);
    }

    @Override
    public String toString() {
        return
                "ItemResults{" +
                        "overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",original_title = '" + originalTitle + '\'' +
                        ",video = '" + video + '\'' +
                        ",title = '" + title + '\'' +
                        ",genre_ids = '" + genreIds + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",popularity = '" + popularity + '\'' +
                        ",id = '" + id + '\'' +
                        ",adult = '" + adult + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        "}";
    }
}
