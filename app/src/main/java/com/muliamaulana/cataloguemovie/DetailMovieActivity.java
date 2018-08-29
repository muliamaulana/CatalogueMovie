package com.muliamaulana.cataloguemovie;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.adapter.CompaniesAdapter;
import com.muliamaulana.cataloguemovie.adapter.CreditsAdapter;
import com.muliamaulana.cataloguemovie.adapter.GenreAdapter;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.database.FavoriteHelper;
import com.muliamaulana.cataloguemovie.model.CreditsMovieModel;
import com.muliamaulana.cataloguemovie.model.DateTimeFormat;
import com.muliamaulana.cataloguemovie.model.DetailModel;
import com.muliamaulana.cataloguemovie.provider.FavoriteColumns;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_IDMOVIE = "movie_id", EXTRA_TITLE_MOVIE = "movie_title", EXTRA_YEAR = "year_release";

    private TextView tvJudulDetail, tvReleaseDetail, tvOverviewDetail, tvVote, tvRuntime;
    private ImageView imgPosterDetail, imgBackdrop;
    private FloatingActionButton fab;
    private LinearLayout mDetailLayout,layoutErrorMsg;
    private CoordinatorLayout mLayout;
    private ProgressBar progressBar;
    private Button btnRetry;
    private Context context;

    private RecyclerView recyclerView, rvCompanies,rvGenre;
    private CreditsAdapter creditsAdapter;
    private CompaniesAdapter companiesAdapter;
    private GenreAdapter genreAdapter;
    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;

    private DetailModel itemDetail;
    private int movie_id;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movie_id = getIntent().getIntExtra(EXTRA_IDMOVIE, 0);
        String titleMovie = getIntent().getStringExtra(EXTRA_TITLE_MOVIE);
        String year = getIntent().getStringExtra(EXTRA_YEAR);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titleMovie);
        getSupportActionBar().setSubtitle(year);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLayout = findViewById(R.id.coordinator_detail);
        mDetailLayout = findViewById(R.id.content_detail_layout);
        mDetailLayout.setVisibility(View.INVISIBLE);
        fab = findViewById(R.id.fab);
        tvJudulDetail = findViewById(R.id.tv_title_detail);
        tvReleaseDetail = findViewById(R.id.tv_release_date_detail);
        tvOverviewDetail = findViewById(R.id.tv_overview_detail);
        tvVote = findViewById(R.id.tv_vote);
        imgPosterDetail = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvRuntime = findViewById(R.id.tv_runtime);
//        tvGenre = findViewById(R.id.tv_genres);
        recyclerView = findViewById(R.id.rv_credits);
        rvCompanies = findViewById(R.id.rv_companies);
        rvGenre = findViewById(R.id.rv_genre);
        progressBar = findViewById(R.id.loading_detailMovie);
        layoutErrorMsg = findViewById(R.id.error_msg_detailMovie);
        btnRetry = findViewById(R.id.btn_retry_detailMovie);
        ratingBar = findViewById(R.id.ratingBarDetail);


        fab.setVisibility(View.INVISIBLE);

        loadMovieDetail(movie_id);
        loadMovieCredit(movie_id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share_detail:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TITLE, itemDetail.getTitle());
                intentShare.putExtra(Intent.EXTRA_SUBJECT, itemDetail.getTitle());
                intentShare.putExtra(Intent.EXTRA_TEXT, itemDetail.getTitle() + "\n\n" + itemDetail.getOverview());
                startActivity(Intent.createChooser(intentShare, getResources().getString(R.string.label_share) + " ─ " + itemDetail.getTitle()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadMovieDetail(int movie_id) {
        progressBar.setVisibility(View.VISIBLE);

        setupCompaniesList();
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<DetailModel> detailCall = movieInterface.getDetailMovie(movie_id, BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        detailCall.enqueue(new Callback<DetailModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailModel> call, @NonNull Response<DetailModel> response) {
                itemDetail = response.body();
                Log.e("Movie Detail", response.toString());

                //Cek Favorit
                favoriteHelper = new FavoriteHelper(DetailMovieActivity.this);
                favoriteHelper.open();
                Cursor cursor = getContentResolver().query(
                        Uri.parse(CONTENT_URI + "/" + itemDetail.getId()),
                        null,
                        null,
                        null,
                        null
                );
                if (cursor != null) {
                    if (cursor.moveToFirst()) isFavorite = true;
                    cursor.close();
                }
                FavoriteSet();

                if (response.isSuccessful()) {
                    mDetailLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    Glide.with(DetailMovieActivity.this).load(BuildConfig.IMG_URL + itemDetail.getPosterPath())
                            .apply(new RequestOptions().placeholder(R.drawable.img_default).centerCrop())
                            .into(imgPosterDetail);
                    Glide.with(DetailMovieActivity.this).load(BuildConfig.IMG_URL + itemDetail.getBackdropPath())
                            .into(imgBackdrop);
                    tvJudulDetail.setText(itemDetail.getTitle());
                    tvOverviewDetail.setText(itemDetail.getOverview());
                    tvVote.setText(itemDetail.getVoteAverage()/2 + " (" + itemDetail.getVoteCount() + ")");
                    float rating = (float) (itemDetail.getVoteAverage()/2);
                    ratingBar.setRating(rating);

                    int runtime = itemDetail.getRuntime();
                    int hours = runtime/60;
                    int minutes = runtime % 60;
                    tvRuntime.setText(hours + getString(R.string.hours) +" "+ minutes + getString(R.string.minutes));

//                    StringBuilder genres = new StringBuilder();
//                    int size = itemDetail.getGenres().size();
//                    for (int i = 0; i < size; i++) {
//                        genres.append(itemDetail.getGenres().get(i).getName()).append(i + 1 < size ? ", " : "");
//                    }

                    tvReleaseDetail.setText(DateTimeFormat.getLongDate(itemDetail.getReleaseDate()));
                    if (itemDetail.getGenres().size() < 3){
                        genreAdapter = new GenreAdapter();
                        rvGenre.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rvGenre.setHasFixedSize(true);
                        rvGenre.setAdapter(genreAdapter);
                    } else {
                        setupGenreList();
                    }
                    genreAdapter.replaceAll(itemDetail.getGenres());
                    companiesAdapter.replaceAll(itemDetail.getProductionCompanies());

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isFavorite) RemoveFavorite();
                            else AddtoFavorite();

                            isFavorite = !isFavorite;
                            FavoriteSet();
                        }

                        private void AddtoFavorite() {
                            ContentValues cv = new ContentValues();
                            cv.put(FavoriteColumns.COLUMN_ID, itemDetail.getId());
                            cv.put(FavoriteColumns.COLUMN_TITLE, itemDetail.getTitle());
                            cv.put(FavoriteColumns.COLUMN_BACKDROP, itemDetail.getBackdropPath());
                            cv.put(FavoriteColumns.COLUMN_POSTER, itemDetail.getPosterPath());
                            cv.put(FavoriteColumns.COLUMN_RELEASE_DATE, itemDetail.getReleaseDate());
                            cv.put(FavoriteColumns.COLUMN_VOTE, itemDetail.getVoteAverage());
                            cv.put(FavoriteColumns.COLUMN_OVERVIEW, itemDetail.getOverview());

                            getContentResolver().insert(CONTENT_URI, cv);

                            Snackbar snackbar = Snackbar.make(mLayout, itemDetail.getTitle() + " "
                                    + getApplicationContext().getString(R.string.added_tofavorite) + " "
                                    + getApplicationContext().getString(R.string.label_myfavorite), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        private void RemoveFavorite() {
                            getContentResolver().delete(
                                    Uri.parse(CONTENT_URI + "/" + itemDetail.getId()),
                                    null,
                                    null
                            );
                            Snackbar snackbar = Snackbar.make(mLayout, itemDetail.getTitle() + " "
                                    + getApplicationContext().getString(R.string.removed_fromfavorite) + " "
                                    + getApplicationContext().getString(R.string.label_myfavorite), Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {
                dispErrorMsg();
            }
        });
    }

    private void FavoriteSet() {
        if (isFavorite) fab.setImageResource(R.drawable.ic_favorite);
        else fab.setImageResource(R.drawable.ic_unfavorite);
    }

    private void setupCreditList() {
        creditsAdapter = new CreditsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(creditsAdapter);
    }
    private void setupGenreList() {
        genreAdapter = new GenreAdapter();
        rvGenre.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
        rvGenre.setHasFixedSize(true);
        rvGenre.setAdapter(genreAdapter);
    }

    private void setupCompaniesList() {
        companiesAdapter = new CompaniesAdapter();
        rvCompanies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCompanies.setHasFixedSize(true);
        rvCompanies.setAdapter(companiesAdapter);
    }

    private void loadMovieCredit(int movie_id) {
        setupCreditList();
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<CreditsMovieModel> creditsCall = movieInterface.getCreditsMovie(movie_id, BuildConfig.API_KEY, BuildConfig.LANGUAGE);
        creditsCall.enqueue(new Callback<CreditsMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<CreditsMovieModel> call, @NonNull Response<CreditsMovieModel> response) {
                final CreditsMovieModel itemCredit = response.body();
                if (response.isSuccessful()) {
                    creditsAdapter.replaceAll(itemCredit.getCast());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreditsMovieModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void dispErrorMsg() {
        progressBar.setVisibility(View.INVISIBLE);
        layoutErrorMsg.setVisibility(View.VISIBLE);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovieDetail(movie_id);
                loadMovieCredit(movie_id);
                layoutErrorMsg.setVisibility(View.INVISIBLE);
            }
        });
    }
}
