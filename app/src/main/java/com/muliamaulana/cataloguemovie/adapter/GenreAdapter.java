package com.muliamaulana.cataloguemovie.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.detail.GenresItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muliamaulana on 27/03/18.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private List<GenresItem> genre = new ArrayList<>();
    private Context context;

    public GenreAdapter() {
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    public void replaceAll(List<GenresItem> results) {
        genre.clear();
        genre = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        holder.BinData(genre.get(position));
    }

    @Override
    public int getItemCount() {
        return genre.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        TextView genreName;
        CardView cardView;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.tv_genre);
            cardView = itemView.findViewById(R.id.card_view_genre);
        }

        public void BinData(final GenresItem genre) {
            genreName.setText(genre.getName());
            cardView.setOnClickListener(new ItemClickListener(new ItemClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(View view) {
                    String id = String.valueOf(genre.getId());
                    Toast.makeText(itemView.getContext(), id, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
