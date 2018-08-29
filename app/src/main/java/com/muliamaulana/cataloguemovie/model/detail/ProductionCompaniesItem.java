package com.muliamaulana.cataloguemovie.model.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by muliamaulana on 19/03/18.
 */

public class ProductionCompaniesItem {

    @SerializedName("id")
    private String id;

    @SerializedName("logo_path")
    private String logo_path;

    @SerializedName("name")
    private String name;

    @SerializedName("origin_country")
    private  String origin_country;

    public ProductionCompaniesItem(String id, String logo_path, String name, String origin_country) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
    }

    public String getId() {
        return id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getName() {
        return name;
    }

    public String getOrigin_country() {
        return origin_country;
    }
}
