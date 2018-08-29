package com.muliamaulana.cataloguemovie;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muliamaulana.cataloguemovie.adapter.FavoriteAdapter;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Cursor listFavorite;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.rv_favorite);

        getSupportActionBar().setTitle(getResources().getString(R.string.label_myfavorite));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupFavoriteList();
        new loadDataAsync().execute();

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

    @Override
    public void onResume() {
        super.onResume();
        new loadDataAsync().execute();
    }

    private void setupFavoriteList() {
        adapter = new FavoriteAdapter(listFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            listFavorite = cursor;
            adapter.replaceAll(listFavorite);
        }
    }
}
