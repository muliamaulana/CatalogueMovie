package com.muliamaulana.cataloguemovie.model.people;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastCombined {
    @SerializedName("id")
    public int id;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("episode_count")
    public int episodeCount;

    @SerializedName("overview")
    public String overview;

    @SerializedName("origin_country")
    public List<OriginCountry> originCountry = null;

    @SerializedName("original_name")
    public String originalName;

    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    @SerializedName("name")
    public String name;

    @SerializedName("media_type")
    public String mediaType;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("first_air_date")
    public String firstAirDate;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("character")
    public String character;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    public Float popularity;

    @SerializedName("credit_id")
    public String creditId;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("video")
    public Boolean video;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("title")
    public String title;

    @SerializedName("adult")
    public Boolean adult;

    public CastCombined(){

    }

    public CastCombined(int id, String originalLanguage, int episodeCount, String overview, List<OriginCountry> originCountry, String originalName, List<Integer> genreIds, String name, String mediaType, String posterPath, String firstAirDate, float voteAverage, int voteCount, String character, String backdropPath, Float popularity, String creditId, String originalTitle, Boolean video, String releaseDate, String title, Boolean adult) {
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.episodeCount = episodeCount;
        this.overview = overview;
        this.originCountry = originCountry;
        this.originalName = originalName;
        this.genreIds = genreIds;
        this.name = name;
        this.mediaType = mediaType;
        this.posterPath = posterPath;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.character = character;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.creditId = creditId;
        this.originalTitle = originalTitle;
        this.video = video;
        this.releaseDate = releaseDate;
        this.title = title;
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public String getOverview() {
        return overview;
    }

    public List<OriginCountry> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalName() {
        return originalName;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getName() {
        return name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getCharacter() {
        return character;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Boolean getVideo() {
        return video;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getAdult() {
        return adult;
    }
}
