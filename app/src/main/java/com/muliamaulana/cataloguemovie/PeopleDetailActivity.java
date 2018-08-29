package com.muliamaulana.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.adapter.people.CombinedCreditAdapter;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.model.people.CastCombined;
import com.muliamaulana.cataloguemovie.model.people.CombinedCreditsModel;
import com.muliamaulana.cataloguemovie.model.people.PersonModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDetailActivity extends AppCompatActivity {
    private static final String TAG = "Person Detail";
    public static String EXTRA_PERSON_NAME = "person_name", EXTRA_PERSON_ID = "person_id";
    private TextView tvName, tvPlaceBirth, tvBirthday, tvBiography;
    private ImageView imgPerson;

    private RecyclerView recyclerView;
    private CombinedCreditAdapter combinedCreditAdapter;

    private PersonModel itemDetail;
    private int person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
        person_id = getIntent().getIntExtra(EXTRA_PERSON_ID,0);
        String person_name = getIntent().getStringExtra(EXTRA_PERSON_NAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(person_name);

        recyclerView = findViewById(R.id.rv_combined_credit);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tvName = findViewById(R.id.person_name);
        tvPlaceBirth = findViewById(R.id.person_place_of_birth);
        tvBirthday = findViewById(R.id.person_birthday);
        tvBirthday = findViewById(R.id.person_biography);
        imgPerson = findViewById(R.id.img_person_detail);

        loadPersonDetail();
//        setupSearchList();
    }

    private void setupSearchList() {
        combinedCreditAdapter = new CombinedCreditAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(combinedCreditAdapter);
        loadCreditCombined(person_id);
    }

    private void loadCreditCombined(int person_id) {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<CombinedCreditsModel> combinedCall = movieInterface.getCombinedCredit(person_id,BuildConfig.API_KEY,BuildConfig.LANGUAGE);
        combinedCall.enqueue(new Callback<CombinedCreditsModel>() {
            @Override
            public void onResponse(@NonNull Call<CombinedCreditsModel> call, @NonNull Response<CombinedCreditsModel> response) {
                if (response.isSuccessful()){
                    Log.d("Combined Credit", response.toString());
                    List<CastCombined> item = response.body().getCastCombined();

                    combinedCreditAdapter.replaceAll(item);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CombinedCreditsModel> call, @NonNull Throwable t) {
                Log.e("Person",t.toString());
            }
        });
    }

    private void loadPersonDetail() {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<PersonModel> detailCall = movieInterface.getDetailPeople(person_id,BuildConfig.API_KEY,BuildConfig.LANGUAGE);
        detailCall.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                itemDetail = response.body();
                Log.e("Person",response.toString());

                if (response.isSuccessful()){
                    Log.e(TAG,response.toString());
                    tvName.setText(itemDetail.getName());
                    tvPlaceBirth.setText(itemDetail.getPlace_of_birth());
                    tvBirthday.setText(itemDetail.getBirthday());
                    tvBiography.setText(itemDetail.getBiography());
                    Glide.with(PeopleDetailActivity.this).load(BuildConfig.IMG_URL+itemDetail.getProfile_path())
                            .apply(new RequestOptions().placeholder(R.drawable.img_default).centerCrop())
                            .into(imgPerson);
                }
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.e("Person",t.toString());
            }
        });
    }
}
