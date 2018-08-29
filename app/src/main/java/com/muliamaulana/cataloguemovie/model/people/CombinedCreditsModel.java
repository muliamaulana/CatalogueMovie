package com.muliamaulana.cataloguemovie.model.people;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedCreditsModel {

    @SerializedName("cast")
    public List<CastCombined> cast;

    @SerializedName("crew")
    public List<Object> crew;

    @SerializedName("id")
    public Integer id;

    public CombinedCreditsModel() {
    }

    public CombinedCreditsModel(List<CastCombined> cast, List<Object> crew, Integer id) {
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public List<CastCombined> getCastCombined() {
        return cast;
    }

    public List<Object> getCrew() {
        return crew;
    }

    public Integer getId() {
        return id;
    }
}
