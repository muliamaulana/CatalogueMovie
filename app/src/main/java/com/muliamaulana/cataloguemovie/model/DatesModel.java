package com.muliamaulana.cataloguemovie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class DatesModel {
    @SerializedName("maximum")
    private String maximum;

    @SerializedName("minimum")
    private String minimum;

    public DatesModel(String maximum, String minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public String getMinimum() {
        return minimum;
    }
}
