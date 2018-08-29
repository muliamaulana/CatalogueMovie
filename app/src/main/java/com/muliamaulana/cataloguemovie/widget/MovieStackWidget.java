package com.muliamaulana.cataloguemovie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.muliamaulana.cataloguemovie.R;

/**
 * Implementation of App Widget functionality.
 */
public class MovieStackWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.muliamaulana.cataloguemovie.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.muliamaulana.cataloguemovie.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.movie_stack_widget);
        remoteViews.setRemoteAdapter(R.id.stack_view, intent);
        remoteViews.setEmptyView(R.id.stack_view,R.id.empty_view);

        Intent intentToast = new Intent(context, MovieStackWidget.class);

        intentToast.setAction(MovieStackWidget.TOAST_ACTION);
        intentToast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntentToast = PendingIntent.getBroadcast(context,0,intentToast, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stack_view, pendingIntentToast);

        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM,0);
            Toast.makeText(context,"Touched Movie" +viewIndex,Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}

