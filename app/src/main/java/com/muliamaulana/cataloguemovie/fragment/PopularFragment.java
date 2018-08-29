package com.muliamaulana.cataloguemovie.fragment;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.adapter.PaginationScrollListener;
import com.muliamaulana.cataloguemovie.adapter.PopularAdapter;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.model.ItemResults;
import com.muliamaulana.cataloguemovie.model.NowPlayingModel;
import com.muliamaulana.cataloguemovie.model.PopularModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private PopularAdapter movieAdapter;
    private ProgressBar progressBar;
    private LinearLayout layoutErrorMsg;
    private Button btnRetry;
    private FloatingActionButton fab;

    private GridLayoutManager layoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        context = view.getContext();
        progressBar = view.findViewById(R.id.loading_popular);
        recyclerView = view.findViewById(R.id.rv_popular);
        layoutErrorMsg = view.findViewById(R.id.error_msg_popular);
        btnRetry = view.findViewById(R.id.btn_retry_popular);
        fab = view.findViewById(R.id.scrollup_popular);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        setupDataList();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutLandscape();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutPotrait();
        }
    }

    private void layoutLandscape() {
        layoutManager = new GridLayoutManager(context,4);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void layoutPotrait(){
        layoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupDataList() {
        progressBar.setVisibility(View.VISIBLE);
        movieAdapter = new PopularAdapter(context);

        int orientation = context.getResources().getConfiguration().orientation;
        layoutPotrait();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutLandscape();
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                super.onScrolled(recyclerView, dx, dy);
                if (visibleItemPosition < 10){
                    fab.hide();
                } else if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }

            @Override
            protected void loadMoreItem() {
                isLoading = true;
                currentPage +=1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                },1000);
            }
            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();
    }

    private void loadFirstPage() {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<PopularModel> popularCall = movieInterface.getPopularMovie(BuildConfig.API_KEY,BuildConfig.LANGUAGE,currentPage);
        popularCall.enqueue(new Callback<PopularModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularModel> call, @NonNull Response<PopularModel> response) {
                if (response.isSuccessful()){
                    Log.e("Popular", response.toString());
                    List<ItemResults> results = response.body().getResults();
                    movieAdapter.addAll(results);
                    progressBar.setVisibility(View.INVISIBLE);

                    if (currentPage <= TOTAL_PAGES) movieAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    dispErrorMsg();
                }
            }
            @Override
            public void onFailure(@NonNull Call<PopularModel> call, @NonNull Throwable t) {
                dispErrorMsg();
            }
        });
    }

    private void loadNextPage() {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<NowPlayingModel> nowplayingCall = movieInterface.getNowPlayingMovie(BuildConfig.API_KEY,BuildConfig.LANGUAGE,currentPage);
        nowplayingCall.enqueue(new Callback<NowPlayingModel>() {
            @Override
            public void onResponse(@NonNull Call<NowPlayingModel> call, @NonNull Response<NowPlayingModel> response) {
                Log.e("Now Playing", response.toString());
                movieAdapter.removedLoadingFooter();
                isLoading = false;

                List<ItemResults> results = response.body().getResults();
                movieAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) movieAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(@NonNull Call<NowPlayingModel> call, @NonNull Throwable t) {
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
                setupDataList();
                layoutErrorMsg.setVisibility(View.INVISIBLE);
            }
        });
    }
}
