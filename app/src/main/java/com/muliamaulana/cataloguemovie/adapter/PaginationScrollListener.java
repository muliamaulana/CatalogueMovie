package com.muliamaulana.cataloguemovie.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by muliamaulana on 19/04/18.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager linearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int visibleThreshold = 10;

        if (!isLoading() && !isLastPage()){
            if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleThreshold) && totalItemCount >= getTotalPageCount()){
                loadMoreItem();
            }
        }
//        if (!isLoading() && !isLastPage()){
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                    && totalItemCount >= getTotalPageCount()){
//                loadMoreItem();
//            }
//        }
    }

    protected abstract void loadMoreItem();
    public abstract int getTotalPageCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();


}
