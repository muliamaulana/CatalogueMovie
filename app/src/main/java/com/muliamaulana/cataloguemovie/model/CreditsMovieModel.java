package com.muliamaulana.cataloguemovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by muliamaulana on 27/03/18.
 */

public class CreditsMovieModel {
    @SerializedName("cast")
    private List<CastMovieResults> cast;

    public CreditsMovieModel(List<CastMovieResults> cast) {
        this.cast = cast;
    }

    public List<CastMovieResults> getCast() {
        return cast;
    }
}
