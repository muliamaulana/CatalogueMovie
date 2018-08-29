package com.muliamaulana.cataloguemovie.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.DetailMovieActivity;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.DateTimeFormat;
import com.muliamaulana.cataloguemovie.model.ItemResults;
import com.muliamaulana.cataloguemovie.provider.FavoriteColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by muliamaulana on 22/04/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private List<ItemResults> resultsList = new ArrayList<>();
    private PopupMenu popupMenu;


    public SearchAdapter() {
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new MovieViewHolder(view);
    }

    public void clearAll() {
        resultsList.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<ItemResults> itemResults) {
        resultsList.clear();
        resultsList = itemResults;
        notifyDataSetChanged();
    }

    public void updateData(List<ItemResults> itemResults) {
        resultsList.addAll(itemResults);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MovieViewHolder holder, int position) {
        holder.BindData(resultsList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView imgPoster, btnMoreCard;
        CardView cardView;
        RatingBar ratingBar;

        public MovieViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_search);
            tvTitle = itemView.findViewById(R.id.title_search);
            tvOverview = itemView.findViewById(R.id.overview_search);
            tvReleaseDate = itemView.findViewById(R.id.release_date_search);
            imgPoster = itemView.findViewById(R.id.img_search);
            btnMoreCard = itemView.findViewById(R.id.btn_more_search);
            ratingBar = itemView.findViewById(R.id.ratingBarSearch);
        }

        public void BindData(final ItemResults itemResults) {

            tvTitle.setText(itemResults.getTitle());
            float rating = (float) (itemResults.getVoteAverage()/2);
            ratingBar.setRating(rating);
            String overview;
            if (itemResults.getOverview().length() > 140) {
                overview = itemResults.getOverview().substring(0, 140) + "...";
            } else {
                overview = itemResults.getOverview();
            }
            tvOverview.setText(overview);

            String dateStr = itemResults.getReleaseDate();
            final SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
            final SimpleDateFormat formatDisplay = new SimpleDateFormat("d MMMM yyyy");
            try {
                Date tanggal = formatTanggal.parse(dateStr);
                String displayTanggal = formatDisplay.format(tanggal);
                //menampilkan tanggal pada textview
                tvReleaseDate.setText(displayTanggal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(itemView.getContext())
                    .load(BuildConfig.IMG_THUMB_URL + itemResults.getPosterPath())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.img_default)
                            .centerCrop())
                    .into(imgPoster);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetail = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    String year = DateTimeFormat.getYear(itemResults.getReleaseDate());
                    intentDetail.putExtra("year_release", year);
                    intentDetail.putExtra("movie_id", itemResults.getId());
                    intentDetail.putExtra("movie_title", itemResults.getTitle());
                    itemView.getContext().startActivity(intentDetail);
                }
            });

            btnMoreCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu = new PopupMenu(itemView.getContext(), v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_card, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_addtofavorite:
                                    AddtoFavorite();
                                    Toast.makeText(itemView.getContext(), itemResults.getTitle()
                                            + " " + itemView.getContext().getString(R.string.added_tofavorite)
                                            + " " + itemView.getContext().getString(R.string.label_myfavorite), Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.action_share:
                                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                                    intentShare.setType("text/plain");
                                    intentShare.putExtra(Intent.EXTRA_TITLE, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_SUBJECT, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_TEXT, itemResults.getTitle() + "\n\n" + itemResults.getOverview());
                                    itemView.getContext().startActivity(Intent
                                            .createChooser(intentShare, itemView.getContext().getString(R.string.label_share) +" ─ "+ itemResults.getTitle()));
                                    return true;

                            }
                            return false;
                        }

                        private void AddtoFavorite() {
                            ContentValues cv = new ContentValues();
                            cv.put(FavoriteColumns.COLUMN_ID, itemResults.getId());
                            cv.put(FavoriteColumns.COLUMN_TITLE, itemResults.getTitle());
                            cv.put(FavoriteColumns.COLUMN_BACKDROP, itemResults.getBackdropPath());
                            cv.put(FavoriteColumns.COLUMN_POSTER, itemResults.getPosterPath());
                            cv.put(FavoriteColumns.COLUMN_RELEASE_DATE, itemResults.getReleaseDate());
                            cv.put(FavoriteColumns.COLUMN_VOTE, itemResults.getVoteAverage());
                            cv.put(FavoriteColumns.COLUMN_OVERVIEW, itemResults.getOverview());

                            itemView.getContext().getContentResolver().insert(CONTENT_URI, cv);

//                            Snackbar snackbar = Snackbar.make(mLayout, itemDetail.getTitle() + " "
//                                    + getApplicationContext().getString(R.string.label_add_favorite) + " "
//                                    + getApplicationContext().getString(R.string.label_myfavorite), Snackbar.LENGTH_SHORT);
//                            snackbar.show();

                        }
                    });
                }
            });
        }
    }
}
