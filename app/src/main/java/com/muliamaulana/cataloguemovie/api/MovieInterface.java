package com.muliamaulana.cataloguemovie.api;

import com.muliamaulana.cataloguemovie.model.CreditsMovieModel;
import com.muliamaulana.cataloguemovie.model.DetailModel;
import com.muliamaulana.cataloguemovie.model.NowPlayingModel;
import com.muliamaulana.cataloguemovie.model.PopularModel;
import com.muliamaulana.cataloguemovie.model.SearchModel;
import com.muliamaulana.cataloguemovie.model.UpcomingModel;
import com.muliamaulana.cataloguemovie.model.people.CombinedCreditsModel;
import com.muliamaulana.cataloguemovie.model.people.PersonModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by muliamaulana on 18/03/18.
 */

public interface MovieInterface {

    @GET("search/movie")
    Call<SearchModel> getSearchMovie (@Query("query") String query, @Query("api_key") String apiKey, @Query("languange") String languange);

    @GET("movie/upcoming")
    Call<UpcomingModel> getUpcomingMovie (@Query("api_key") String apiKey, @Query("languange") String languange, @Query("page") int pageNumber);

    @GET("movie/now_playing")
    Call<NowPlayingModel> getNowPlayingMovie (@Query("api_key") String apiKey, @Query("languange") String languange, @Query("page") int pageNumber);

    @GET("movie/popular")
    Call<PopularModel> getPopularMovie (@Query("api_key") String apiKey, @Query("languange") String languange, @Query("page") int pageNumber);

    @GET("movie/{movie_id}")
    Call<DetailModel> getDetailMovie(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{movie_id}/credits")
    Call<CreditsMovieModel> getCreditsMovie(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("language") String languange);

    @GET("person/{person_id}/combined_credits")
    Call<CombinedCreditsModel> getCombinedCredit(@Path("person_id") int person_id, @Query("api_key") String apiKey, @Query("language") String languange);

    @GET("person/{person_id}")
    Call<PersonModel> getDetailPeople (@Path("person_id") int person_id, @Query("api_key") String apiKey, @Query("language") String language);

}
