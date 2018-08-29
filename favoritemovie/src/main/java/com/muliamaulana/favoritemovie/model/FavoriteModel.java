package com.muliamaulana.favoritemovie.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.muliamaulana.favoritemovie.provider.DatabaseContract.getColumnDouble;
import static com.muliamaulana.favoritemovie.provider.DatabaseContract.getColumnInt;
import static com.muliamaulana.favoritemovie.provider.DatabaseContract.getColumnString;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_BACKDROP;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_DURATION;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_OVERVIEW;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_POSTER;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_RELEASE_DATE;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_TITLE;
import static com.muliamaulana.favoritemovie.provider.FavoriteColumns.COLUMN_VOTE;

/**
 * Created by muliamaulana on 02/04/18.
 */

public class FavoriteModel {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("overview")
    private String overview;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public FavoriteModel(int id, String title, String backdropPath, String posterPath, String releaseDate, double voteAverage, String overview) {
        this.id = id;
        this.title = title;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public FavoriteModel() {
    }

    public FavoriteModel(Cursor cursor) {
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
                        ",id = '" + id + '\'' +
                        ",title = '" + title + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        "overview = '" + overview + '\'' +
                        "}";
    }
}
