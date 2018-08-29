package com.muliamaulana.cataloguemovie.adapter;

import android.view.View;

/**
 * Created by muliamaulana on 18/04/18.
 */

public class ItemClickListener implements View.OnClickListener {

    private OnItemClickCallback onItemClickCallback;

    public ItemClickListener(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view);
    }
}
