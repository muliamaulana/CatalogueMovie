package com.muliamaulana.favoritemovie.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.favoritemovie.BuildConfig;
import com.muliamaulana.favoritemovie.DetailMovieActivity;
import com.muliamaulana.favoritemovie.MainActivity;
import com.muliamaulana.favoritemovie.R;
import com.muliamaulana.favoritemovie.model.FavoriteModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.muliamaulana.favoritemovie.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by muliamaulana on 02/04/18.
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

    private FavoriteModel getItem(int position){
        if (!listFavorite.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new FavoriteModel(listFavorite);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView imgPoster, btnMoreCard;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            imgPoster = itemView.findViewById(R.id.img_poster);
            btnMoreCard = itemView.findViewById(R.id.btn_more_card);

        }

        public void BindData(final FavoriteModel itemResults) {
            tvTitle.setText(itemResults.getTitle());
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
            btnMoreCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
//                    PopupMenu popupMenu = new PopupMenu(itemView,v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_card, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_detail:
                                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                                    intent.setData(Uri.parse(CONTENT_URI + "/" + itemResults.getId()));
                                    intent.putExtra("movie_title", itemResults.getTitle());
                                    itemView.getContext().startActivity(intent);
                                    return true;
                                case R.id.action_share:
//                                    Toast.makeText(itemView.getContext(), "Share it!", Toast.LENGTH_SHORT).show();
                                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                                    intentShare.setType("text/plain");
                                    intentShare.putExtra(Intent.EXTRA_TITLE, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_SUBJECT, itemResults.getTitle());
                                    intentShare.putExtra(Intent.EXTRA_TEXT, itemResults.getTitle()+"\n\n"+itemResults.getOverview());
                                    itemView.getContext().startActivity(Intent
                                            .createChooser(intentShare,itemView.getResources().getString(R.string.label_share)));
                                    return true;
                            }
                            return false;
                        }

                    });
                }
            });
        }
    }
}
