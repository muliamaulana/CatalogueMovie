package com.muliamaulana.cataloguemovie.api;

import com.muliamaulana.cataloguemovie.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class ClientAPI {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static Retrofit retrofit = null;
    public static Retrofit getMovieDB(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
