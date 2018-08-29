package com.muliamaulana.cataloguemovie.reminder;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.muliamaulana.cataloguemovie.MainActivity;
import com.muliamaulana.cataloguemovie.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by muliamaulana on 08/04/18.
 */

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = context.getResources().getString(R.string.app_name);
        String message = context.getResources().getString(R.string.msg_daily_reminder);

        showReminder(context, title, message);

    }

    private void showReminder(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent_repeating  = new Intent (context, MainActivity.class);
        intent_repeating.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0001,intent_repeating,PendingIntent.FLAG_UPDATE_CURRENT);


        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setSound(notifSound)
                .setVibrate(new long[]{1000, 1000, 1000})
                .setAutoCancel(true);

        notificationManager.notify(0001,notifBuilder.build());
    }

    public void setDailyReminder (Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(context,ReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0001,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void cancelDailyReminder (Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,ReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0001,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
