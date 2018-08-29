package com.muliamaulana.cataloguemovie.model;

import com.google.gson.annotations.SerializedName;
import com.muliamaulana.cataloguemovie.model.detail.BelongsToCollection;
import com.muliamaulana.cataloguemovie.model.detail.GenresItem;
import com.muliamaulana.cataloguemovie.model.detail.ProductionCompaniesItem;
import com.muliamaulana.cataloguemovie.model.detail.ProductionCountriesItem;
import com.muliamaulana.cataloguemovie.model.detail.SpokenLanguagesItem;

import java.util.List;

/**
 * Created by muliamaulana on 19/03/18.
 */

public class DetailModel {
    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("genres")
    private List<GenresItem> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("production_countries")
    private List<ProductionCountriesItem> productionCountries;

    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("budget")
    private int budget;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("spoken_languages")
    private List<SpokenLanguagesItem> spokenLanguages;

    @SerializedName("production_companies")
    private List<ProductionCompaniesItem> productionCompanies;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("belongs_to_collection")
    private BelongsToCollection belongsToCollection;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    public DetailModel(String originalLanguage, String imdbId, boolean video, String title, String backdropPath, int revenue, List<GenresItem> genres, double popularity, List<ProductionCountriesItem> productionCountries, int id, int voteCount, int budget, String overview, String originalTitle, int runtime, String posterPath, List<SpokenLanguagesItem> spokenLanguages, List<ProductionCompaniesItem> productionCompanies, String releaseDate, double voteAverage, BelongsToCollection belongsToCollection, String tagline, boolean adult, String homepage, String status) {
        this.originalLanguage = originalLanguage;
        this.imdbId = imdbId;
        this.video = video;
        this.title = title;
        this.backdropPath = backdropPath;
        this.revenue = revenue;
        this.genres = genres;
        this.popularity = popularity;
        this.productionCountries = productionCountries;
        this.id = id;
        this.voteCount = voteCount;
        this.budget = budget;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.runtime = runtime;
        this.posterPath = posterPath;
        this.spokenLanguages = spokenLanguages;
        this.productionCompanies = productionCompanies;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.belongsToCollection = belongsToCollection;
        this.tagline = tagline;
        this.adult = adult;
        this.homepage = homepage;
        this.status = status;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getRevenue() {
        return revenue;
    }

    public List<GenresItem> getGenres() {
        return genres;
    }

    public double getPopularity() {
        return popularity;
    }

    public List<ProductionCountriesItem> getProductionCountries() {
        return productionCountries;
    }

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getBudget() {
        return budget;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<SpokenLanguagesItem> getSpokenLanguages() {
        return spokenLanguages;
    }

    public List<ProductionCompaniesItem> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getStatus() {
        return status;
    }
}
