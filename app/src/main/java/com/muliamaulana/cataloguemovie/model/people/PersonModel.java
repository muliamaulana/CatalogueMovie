package com.muliamaulana.cataloguemovie.model.people;

import com.google.gson.annotations.SerializedName;
import com.muliamaulana.cataloguemovie.model.detail.PeopleAlsoKnownAs;

import java.util.List;

public class PersonModel {
    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("also_known_as")
    private List<PeopleAlsoKnownAs> alsoKnownAs;

    @SerializedName("gender")
    private int gender;

    @SerializedName("biography")
    private String biography;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("place_of_birth")
    private String place_of_birth;

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("imdb_id")
    private String imdb_id;

    @SerializedName("homepage")
    private String homepage;

    public PersonModel(String birthday, String deathday, int id, String name, List<PeopleAlsoKnownAs> alsoKnownAs, int gender, String biography, float popularity, String place_of_birth, String profile_path, boolean adult, String imdb_id, String homepage) {
        this.birthday = birthday;
        this.deathday = deathday;
        this.id = id;
        this.name = name;
        this.alsoKnownAs = alsoKnownAs;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.place_of_birth = place_of_birth;
        this.profile_path = profile_path;
        this.adult = adult;
        this.imdb_id = imdb_id;
        this.homepage = homepage;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PeopleAlsoKnownAs> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public int getGender() {
        return gender;
    }

    public String getBiography() {
        return biography;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }
}
