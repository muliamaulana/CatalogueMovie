package com.muliamaulana.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.model.ItemResults;

import java.util.concurrent.ExecutionException;

import static com.muliamaulana.cataloguemovie.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by muliamaulana on 07/04/18.
 */

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;

    private Cursor listFavorite;

    public MovieRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        listFavorite = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        listFavorite = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listFavorite.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        ItemResults item = getItem(position);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.movie_item_widget);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.BASE_IMG_URL + "w500" + item.getBackdropPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(MovieStackWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private ItemResults getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Invalid Position");
        }

        return new ItemResults(listFavorite);
    }
}
