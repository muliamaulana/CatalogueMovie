package com.muliamaulana.cataloguemovie.model.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by muliamaulana on 19/03/18.
 */

public class GenresItem {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public GenresItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
