package com.muliamaulana.favoritemovie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.muliamaulana.favoritemovie.model.FavoriteModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_TITLE_MOVIE = "movie_title";

    private TextView tvJudulDetail, tvReleaseDetail, tvOverviewDetail, tvVote;
    private ImageView imgPosterDetail, imgBackdrop;
    private FloatingActionButton fab;

    private FavoriteModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        String titleMovie = getIntent().getStringExtra(EXTRA_TITLE_MOVIE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titleMovie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvJudulDetail = findViewById(R.id.tv_title_detail);
        tvReleaseDetail = findViewById(R.id.tv_release_date_detail);
        tvOverviewDetail = findViewById(R.id.tv_overview_detail);
        tvVote = findViewById(R.id.tv_vote);
        imgPosterDetail = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);

        loadData();
        storeData();
    }

    private void loadData() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) item = new FavoriteModel(cursor);
            cursor.close();
        }
    }

    private void storeData() {
        if (item == null) return;

        Glide.with(this)
                .load(BuildConfig.IMG_URL + item.getBackdropPath())
                .into(imgBackdrop);

        Glide.with(this)
                .load(BuildConfig.IMG_URL + item.getPosterPath())
                .into(imgPosterDetail);

        tvJudulDetail.setText(item.getTitle());
        String releaseDateStr = item.getReleaseDate();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatDisplay = new SimpleDateFormat("EEEE, d MMMM yyyy");
        try {
            Date tanggal = formatTanggal.parse(releaseDateStr);
            String displayTanggal = formatDisplay.format(tanggal);
            tvReleaseDetail.setText(displayTanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvVote.setText(String.valueOf(item.getVoteAverage()));
        tvOverviewDetail.setText(item.getOverview());

    }
}
