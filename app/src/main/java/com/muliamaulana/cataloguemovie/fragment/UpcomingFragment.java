package com.muliamaulana.cataloguemovie.fragment;


import android.app.Activity;
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
import com.muliamaulana.cataloguemovie.adapter.UpcomingAdapter;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.model.ItemResults;
import com.muliamaulana.cataloguemovie.model.NowPlayingModel;
import com.muliamaulana.cataloguemovie.model.UpcomingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private UpcomingAdapter movieAdapter;
    private ProgressBar progressBar;
    private LinearLayout layoutErrorMsg;
    private GridLayoutManager gridLayoutManager;
    private Button btnRetry;
    private FloatingActionButton fab;

    private LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        context = view.getContext();
        progressBar = view.findViewById(R.id.loading_upcoming);
        recyclerView = view.findViewById(R.id.rv_upcoming);
        layoutErrorMsg = view.findViewById(R.id.error_msg_upcoming);
        btnRetry = view.findViewById(R.id.btn_retry_upcoming);
        fab = view.findViewById(R.id.scrollup_upcoming);
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
        gridLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void layoutPotrait(){
        linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupDataList() {
        progressBar.setVisibility(View.VISIBLE);
        movieAdapter = new UpcomingAdapter(context);
        int orientation = context.getResources().getConfiguration().orientation;
        layoutPotrait();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutLandscape();
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

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
        currentPage =1;
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<UpcomingModel> upcomingCall = movieInterface.getUpcomingMovie(BuildConfig.API_KEY,BuildConfig.LANGUAGE,currentPage);
        upcomingCall.enqueue(new Callback<UpcomingModel>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingModel> call, @NonNull Response<UpcomingModel> response) {
                if (response.isSuccessful()){
                    Log.e("Upcoming", response.toString());
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
            public void onFailure(@NonNull Call<UpcomingModel> call, @NonNull Throwable t) {
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
