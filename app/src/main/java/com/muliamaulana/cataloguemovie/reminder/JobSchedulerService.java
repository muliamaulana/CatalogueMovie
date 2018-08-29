package com.muliamaulana.cataloguemovie.reminder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.muliamaulana.cataloguemovie.BuildConfig;
import com.muliamaulana.cataloguemovie.DetailMovieActivity;
import com.muliamaulana.cataloguemovie.R;
import com.muliamaulana.cataloguemovie.api.ClientAPI;
import com.muliamaulana.cataloguemovie.api.MovieInterface;
import com.muliamaulana.cataloguemovie.model.DateTimeFormat;
import com.muliamaulana.cataloguemovie.model.ItemResults;
import com.muliamaulana.cataloguemovie.model.UpcomingModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muliamaulana on 21/04/18.
 */

public class JobSchedulerService extends JobService{

    private static final int PAGE_START = 1;
    private static final String TAG = "JobSchedulerService";
    private int currentPage = PAGE_START;

    @Override
    public boolean onStartJob(JobParameters params) {
        getUpcomingMovie(params);
        return true;
    }

    private void getUpcomingMovie(final JobParameters job) {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<UpcomingModel> upcomingCall = movieInterface.getUpcomingMovie(BuildConfig.API_KEY,BuildConfig.LANGUAGE,currentPage);
        upcomingCall.enqueue(new Callback<UpcomingModel>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingModel> call, @NonNull Response<UpcomingModel> response) {
                List<ItemResults> itemResults = response.body().getResults();

                //upcoming random movie
//                int i = new Random().nextInt(itemResults.size());
//                ItemResults item = itemResults.get(i);
//                String movie_title = item.getTitle();
//                int notif_id = itemResults.get(i).getId();
//                showNotification(getApplicationContext(), movie_title, notif_id, item);

                Calendar date = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd MMMM yyyy");
                String current_date = mdformat.format(date.getTime());

                Log.d(TAG, response.toString());

                for (int i=0; i<itemResults.size(); i++){
                    ItemResults item = itemResults.get(i);
                    if (current_date.equalsIgnoreCase(DateTimeFormat.getShortDate(itemResults.get(i).getReleaseDate()))){
                        String movie_title = itemResults.get(i).getTitle();
                        int notif_id = itemResults.get(i).getId();
                        showNotification(getApplicationContext(), movie_title, notif_id, item);
                        Log.d(TAG, response.toString());
                        jobFinished(job,false);
                    }
                }

                for (int page = PAGE_START; page < response.body().getTotalPages(); page++){
                    for (int i=0; i<itemResults.size(); i++) {
                        ItemResults item = itemResults.get(i);
                        if (current_date.equalsIgnoreCase(DateTimeFormat.getShortDate(itemResults.get(i).getReleaseDate()))) {
                            String movie_title = itemResults.get(i).getTitle();
                            int notif_id = itemResults.get(i).getId();
                            showNotification(getApplicationContext(), movie_title, notif_id, item);
                            Log.d(TAG, response.toString());
                            jobFinished(job, false);

                        }
                    }
                    currentPage += 1;
                    readNextPage(job);
                }
                if (currentPage == response.body().getTotalPages()){
                    Log.d(TAG,"Current Page = "+response.body().getTotalPages());
                    jobFinished(job,false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingModel> call, @NonNull Throwable t) {
                jobFinished(job,true);
            }
        });
    }

    private void readNextPage(final JobParameters job) {
        MovieInterface movieInterface = ClientAPI.getMovieDB().create(MovieInterface.class);
        Call<UpcomingModel> upcomingCall = movieInterface.getUpcomingMovie(BuildConfig.API_KEY,BuildConfig.LANGUAGE,currentPage);
        upcomingCall.enqueue(new Callback<UpcomingModel>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingModel> call, @NonNull Response<UpcomingModel> response) {
                List<ItemResults> itemResults = response.body().getResults();
                Calendar date = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd MMMM yyyy");
                String current_date = mdformat.format(date.getTime());

                Log.d(TAG, response.toString());
                for (int i=0; i<itemResults.size(); i++){
                    ItemResults item = itemResults.get(i);
                    if (current_date.equalsIgnoreCase(DateTimeFormat.getShortDate(itemResults.get(i).getReleaseDate()))){
                        String movie_title = itemResults.get(i).getTitle();
                        int notif_id = itemResults.get(i).getId();
                        showNotification(getApplicationContext(), movie_title, notif_id, item);
                        Log.e("Response Page", response.toString());
                        jobFinished(job,false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingModel> call, @NonNull Throwable t) {
                jobFinished(job,true);
            }
        });
    }


    private void showNotification(Context context, String movie_title, int notif_id, ItemResults item) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra("movie_id", item.getId());
        intent.putExtra("movie_title", item.getTitle());

        PendingIntent pendingIntent = PendingIntent.getActivity(context,notif_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(movie_title)
                .setContentText(movie_title +" "+ getApplicationContext().getString(R.string.msg_upcoming_reminder))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setSound(notifSound)
                .setVibrate(new long[]{1000, 1000, 1000})
                .setAutoCancel(true);

        assert notificationManager != null;
        notificationManager.notify(notif_id,notifBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Stopped");
        return true;
    }
}
