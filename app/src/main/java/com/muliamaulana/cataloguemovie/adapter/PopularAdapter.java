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

import java.util.ArrayList;
import java.util.List;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<ItemResults> resultsList;
    private PopupMenu popupMenu;
    private Context context;

    private boolean isLoadingAdded = false;

    public PopularAdapter(Context context) {
        this.context = context;
        resultsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View viewLoadMore = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoadMore);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemResults itemResults = resultsList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
//                movieViewHolder.tvTitle.setText(itemResults.getTitle());
//                movieViewHolder.tvReleaseDate.setText(DateTimeFormat.getShortDate(itemResults.getReleaseDate()));
//                String overview;
//                if (itemResults.getOverview().length() > 140) {
//                    overview = itemResults.getOverview().substring(0, 140) + "...";
//                } else {
//                    overview = itemResults.getOverview();
//                }
//                movieViewHolder.tvOverview.setText(overview);
                Glide.with(context)
                        .load(BuildConfig.IMG_THUMB_URL + itemResults.getPosterPath())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.img_default)
                                .centerCrop())
                        .into(movieViewHolder.imgPoster);

                movieViewHolder.cardView.setOnClickListener(new ItemClickListener(new ItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View view) {
//                    Toast.makeText(itemView.getContext(),itemResults.getTitle(),Toast.LENGTH_SHORT).show();
                        Intent intentDetail = new Intent(context, DetailMovieActivity.class);
                        String year = DateTimeFormat.getYear(itemResults.getReleaseDate());
                        intentDetail.putExtra("year_release", year);
                        intentDetail.putExtra("movie_id", itemResults.getId());
                        intentDetail.putExtra("movie_title", itemResults.getTitle());
                        context.startActivity(intentDetail);
                    }
                }));

//                movieViewHolder.btnMoreCard.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupMenu = new PopupMenu(context, v);
//                        MenuInflater menuInflater = popupMenu.getMenuInflater();
//                        menuInflater.inflate(R.menu.menu_card, popupMenu.getMenu());
//                        popupMenu.show();
//                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.action_addtofavorite:
//                                        AddtoFavorite();
//                                        Toast.makeText(context, itemResults.getTitle()
//                                                + " " + context.getString(R.string.added_tofavorite)
//                                                + " " + context.getString(R.string.label_myfavorite), Toast.LENGTH_SHORT).show();
//                                        return true;
//                                    case R.id.action_share:
//                                        Intent intentShare = new Intent(Intent.ACTION_SEND);
//                                        intentShare.setType("text/plain");
//                                        intentShare.putExtra(Intent.EXTRA_TITLE, itemResults.getTitle());
//                                        intentShare.putExtra(Intent.EXTRA_SUBJECT, itemResults.getTitle());
//                                        intentShare.putExtra(Intent.EXTRA_TEXT, itemResults.getTitle() + "\n\n" + itemResults.getOverview());
//                                        context.startActivity(Intent
//                                                .createChooser(intentShare, context.getString(R.string.label_share)));
//                                        return true;
//
//                                }
//                                return false;
//                            }
//
//                            private void AddtoFavorite() {
//                                ContentValues cv = new ContentValues();
//                                cv.put(FavoriteColumns.COLUMN_ID, itemResults.getId());
//                                cv.put(FavoriteColumns.COLUMN_TITLE, itemResults.getTitle());
//                                cv.put(FavoriteColumns.COLUMN_BACKDROP, itemResults.getBackdropPath());
//                                cv.put(FavoriteColumns.COLUMN_POSTER, itemResults.getPosterPath());
//                                cv.put(FavoriteColumns.COLUMN_RELEASE_DATE, itemResults.getReleaseDate());
//                                cv.put(FavoriteColumns.COLUMN_VOTE, itemResults.getVoteAverage());
//                                cv.put(FavoriteColumns.COLUMN_OVERVIEW, itemResults.getOverview());
//
//                                context.getContentResolver().insert(CONTENT_URI, cv);
//
////                            Snackbar snackbar = Snackbar.make(mLayout, itemDetail.getTitle() + " "
////                                    + getApplicationContext().getString(R.string.label_add_favorite) + " "
////                                    + getApplicationContext().getString(R.string.label_myfavorite), Snackbar.LENGTH_SHORT);
////                            snackbar.show();
//
//                            }
//                        });
//
//                    }
//                });
                break;

            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return resultsList ==null? 0: resultsList.size();
    }

    public int getItemViewType (int position){
        return (position ==resultsList.size() - 1 && isLoadingAdded) ? LOADING:ITEM;
    }

    public void add(ItemResults itemResults){
        resultsList.add(itemResults);
        notifyItemInserted(resultsList.size()-1);
    }

    public void addAll(List<ItemResults> itemResults){
        for (ItemResults results : itemResults){
            add(results);
        }
    }

    public void remove(ItemResults itemResults){
        int position = resultsList.indexOf(itemResults);
        if (position > -1){
            resultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear(){
        isLoadingAdded = false;
        while (getItemCount() > 0){
            remove(getItem(0));
        }
    }

    public boolean isEmpty(){
        return getItemCount() == 0;
    }

    public void addLoadingFooter(){
        isLoadingAdded = true;
        add(new ItemResults());
    }

    public void removedLoadingFooter(){
        isLoadingAdded = false;

        int position = resultsList.size()-1;
        ItemResults itemResults = getItem(position);

        if (itemResults!=null){
            resultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ItemResults getItem(int position){
        return resultsList.get(position);
    }

    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.item_popular, parent, false);
        viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    public void replaceAll(List<ItemResults> itemResults) {
        resultsList.clear();
        resultsList = itemResults;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView imgPoster, btnMoreCard;
        CardView cardView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_popular);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvOverview = itemView.findViewById(R.id.tv_overview);
//            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            imgPoster = itemView.findViewById(R.id.img_popular);
//            btnMoreCard = itemView.findViewById(R.id.btn_more_card);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View viewLoadMore) {
            super(viewLoadMore);
        }
    }
}