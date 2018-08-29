package com.muliamaulana.cataloguemovie;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.muliamaulana.cataloguemovie.adapter.MovieAdapter;
import com.muliamaulana.cataloguemovie.adapter.SearchAdapter;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.model.ItemResults;
import com.muliamaulana.cataloguemovie.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    public static final String MOVIE_TITLE = "movie_title";
    private static final String TAG = "SearchActivity";
    private RecyclerView recyclerView;
    private SearchAdapter movieAdapter;
    private ProgressBar progressBar;
    private LinearLayout layoutErrorMsg, layoutNotFoundMsg;
    private Button btnRetry,btnSearchAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.rv_search);
        progressBar = findViewById(R.id.loading_search);
        layoutErrorMsg = findViewById(R.id.error_msg_search);
        layoutNotFoundMsg = findViewById(R.id.notfound_msg_search);
        btnRetry = findViewById(R.id.btn_retry_search);
        btnSearchAgain = findViewById(R.id.btn_search_again);

        getSupportActionBar().setTitle(getResources().getString(R.string.label_search_movie));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupSearchList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupSearchList() {
        progressBar.setVisibility(View.VISIBLE);
        movieAdapter = new SearchAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
        final String movie_title = getIntent().getStringExtra(MOVIE_TITLE);
        loadSearchData(movie_title);
    }

    private void loadSearchData(String movie_title) {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<SearchModel> searchCall = movieInterface.getSearchMovie(movie_title, BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        searchCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()){
                    Log.e("Search", response.toString());
                    List<ItemResults> item = response.body().getResults();
                    movieAdapter.replaceAll(item);
                    int search_result = response.body().getTotalResults();
                    if (search_result== 0){
                        Log.d(TAG,"Tidak ditemukan");
                        layoutNotFoundMsg.setVisibility(View.VISIBLE);
                        btnSearchAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }

                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    dispErrorMsg();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                dispErrorMsg();
            }
        });
    }

    private void dispErrorMsg() {
        progressBar.setVisibility(View.INVISIBLE);
        layoutErrorMsg.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupSearchList();
                layoutErrorMsg.setVisibility(View.INVISIBLE);

            }
        });
//        Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
    }
}
