package com.muliamaulana.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.PeopleDetailActivity;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.detail.ProductionCompaniesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muliamaulana on 27/03/18.
 */

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompaniesViewHolder> {

    private List<ProductionCompaniesItem> companies = new ArrayList<>();
    private Context context;

    public CompaniesAdapter() {
    }

    @Override
    public CompaniesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_companies, parent, false);
        return new CompaniesViewHolder(view);
    }

    public void replaceAll(List<ProductionCompaniesItem> results) {
        companies.clear();
        companies = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CompaniesViewHolder holder, int position) {
        holder.BinData(companies.get(position));
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class CompaniesViewHolder extends RecyclerView.ViewHolder {

        TextView nameCompanies, countryCompanies;
        ImageView imgCompanies;
        LinearLayout cardView;

        public CompaniesViewHolder(View itemView) {
            super(itemView);
            nameCompanies= itemView.findViewById(R.id.name_companies);
            countryCompanies = itemView.findViewById(R.id.country_companies);
            imgCompanies = itemView.findViewById(R.id.thumb_companies);
            cardView = itemView.findViewById(R.id.card_view_companies);
        }

        public void BinData(final ProductionCompaniesItem companies) {
            nameCompanies.setText(companies.getName());
            countryCompanies.setText(companies.getOrigin_country());
            Glide.with(itemView.getContext()).load(BuildConfig.IMG_THUMB_URL + companies.getLogo_path())
                .apply(new RequestOptions().placeholder(R.drawable.img_default).fitCenter())
                .into(imgCompanies);
            cardView.setOnClickListener(new ItemClickListener(new ItemClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(View view) {
//                    String id = String.valueOf(companies.getId());
//                    Toast.makeText(itemView.getContext(),id,Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
