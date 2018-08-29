package com.muliamaulana.cataloguemovie.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.DetailMovieActivity;
import com.muliamaulana.cataloguemovie.MainActivity;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.DateTimeFormat;
import com.muliamaulana.cataloguemovie.model.ItemResults;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by muliamaulana on 31/03/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Cursor listFavorite;

    public FavoriteAdapter(Cursor itemFavorite){
        replaceAll(itemFavorite);
    }

    public void replaceAll(Cursor itemFavorite) {
        listFavorite = itemFavorite;
        notifyDataSetChanged();
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        holder.BindData(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (listFavorite == null){
            return 0;
        }
        return listFavorite.getCount();
    }
    public boolean isEmpty(){
        return getItemCount() == 0;
    }

    private ItemResults getItem(int position){
        if (!listFavorite.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new ItemResults(listFavorite);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView imgPoster, btnMoreCard;
        CardView cardView;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            imgPoster = itemView.findViewById(R.id.img_poster);
            btnMoreCard = itemView.findViewById(R.id.btn_more_card);
            ratingBar = itemView.findViewById(R.id.ratingBarCard);
        }

        public void BindData(final ItemResults itemResults) {
            tvTitle.setText(itemResults.getTitle());
            float rating = (float) itemResults.getVoteAverage()/2;
            ratingBar.setRating(rating);
            String overview;
            if (itemResults.getOverview().length() > 140) {
                overview = itemResults.getOverview().substring(0, 140) + "...";
            } else {
                overview = itemResults.getOverview();
            }
            tvOverview.setText(overview);
            tvReleaseDate.setText(DateTimeFormat.getShortDate(itemResults.getReleaseDate()));

            Glide.with(itemView.getContext())
                    .load(BuildConfig.IMG_THUMB_URL + itemResults.getPosterPath())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.img_default)
                            .centerCrop())
                    .into(imgPoster);

            cardView.setOnClickListener(new ItemClickListener(new ItemClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(View view) {
                    Intent intentDetail = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    String year = DateTimeFormat.getYear(itemResults.getReleaseDate());
                    intentDetail.putExtra("year_release", year);
                    intentDetail.putExtra("movie_id", itemResults.getId());
                    intentDetail.putExtra("movie_title", itemResults.getTitle());
                    itemView.getContext().startActivity(intentDetail);
                }
            }));

            btnMoreCard.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
//                    PopupMenu popupMenu = new PopupMenu(itemView,v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_card_fav, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_removefavorite:
                                    RemoveFavorite(itemResults.getId());
                                    Toast.makeText(itemView.getContext(), itemResults.getTitle()
                                            +" "+itemView.getContext().getString(R.string.removed_fromfavorite)
                                            +" "+itemView.getContext().getString(R.string.label_myfavorite), Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.action_share_fav:
                                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                                    intentShare.setType("text/plain");
                                    intentShare.putExtra(Intent.EXTRA_TITLE, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_SUBJECT, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_TEXT, itemResults.getTitle()+"\n\n"+itemResults.getOverview());
                                    itemView.getContext().startActivity(Intent
                                            .createChooser(intentShare, itemView.getContext().getString(R.string.label_share) +" ─ "+ itemResults.getTitle()));
                                    return true;
                            }
                            return false;
                        }

                        private void RemoveFavorite(int id) {
                                itemView.getContext().getContentResolver().delete(
                                        Uri.parse(CONTENT_URI + "/" + id),
                                        null,
                                        null
                                );
                                notifyItemRemoved(id);

//                                Snackbar snackbar = Snackbar.make(mLayout,itemDetail.getTitle()+" "
//                                        + getApplicationContext().getString(R.string.removed_fromfavorite)+" "
//                                        + getApplicationContext().getString(R.string.label_myfavorite),Snackbar.LENGTH_SHORT);
//                                snackbar.show();

                        }

                    });
                }
            });
        }
    }
}
