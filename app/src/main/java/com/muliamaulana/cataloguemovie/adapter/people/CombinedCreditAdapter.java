package com.muliamaulana.cataloguemovie.adapter.people;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.people.CastCombined;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by muliamaulana on 22/04/18.
 */

public class CombinedCreditAdapter extends RecyclerView.Adapter<CombinedCreditAdapter.CreditViewHolder>{

    public List<CastCombined> castCombinedList = new ArrayList<>();

    public CombinedCreditAdapter(){

    }

    @Override
    public CreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_credit,parent,false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditViewHolder holder, int position) {
        holder.BindData(castCombinedList.get(position));
    }

    public void clearAll(){
        castCombinedList.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<CastCombined> combinedList){
        castCombinedList.clear();
        castCombinedList=combinedList;
        notifyDataSetChanged();
    }

    public void updateData(List<CastCombined> combinedList){
        castCombinedList.addAll(combinedList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return castCombinedList.size();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView imgPoster;
        RatingBar ratingBar;

        public CreditViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_people_credit);
            tvTitle = itemView.findViewById(R.id.title_people_credit);
            tvOverview = itemView.findViewById(R.id.overview_people_credit);
            tvReleaseDate = itemView.findViewById(R.id.release_date_people_credit);
            imgPoster = itemView.findViewById(R.id.img_people_credit);
            ratingBar = itemView.findViewById(R.id.ratingBar_people_credit);
        }

        public void BindData(CastCombined castCombined) {
            tvTitle.setText(castCombined.getTitle());
            float rating = (float) (castCombined.getVoteAverage()/2);
            ratingBar.setRating(rating);

            String overview;
            if (castCombined.getOverview().length()>140){
                overview = castCombined.getOverview().substring(0,140)+"...";
            } else {
                overview = castCombined.getOverview();
            }
            tvOverview.setText(overview);

            String dateStr = castCombined.getReleaseDate();
            SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatDisplay = new SimpleDateFormat("d MMMM yyyy");
            try {
                Date tanggal = formatTanggal.parse(dateStr);
                String displayTanggal = formatDisplay.format(tanggal);
                tvReleaseDate.setText(displayTanggal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(itemView.getContext()).load(BuildConfig.IMG_THUMB_URL + castCombined.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.img_default))
                    .into(imgPoster);
        }
    }
}
