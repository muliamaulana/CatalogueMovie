package com.muliamaulana.cataloguemovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class SearchModel {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<ItemResults> results;

    @SerializedName("total_results")
    private int totalResults;

    public SearchModel(int page, int totalPages, List<ItemResults> results, int totalResults) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ItemResults> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

}
