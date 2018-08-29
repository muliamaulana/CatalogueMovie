package com.muliamaulana.cataloguemovie;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.muliamaulana.cataloguemovie.reminder.JobSchedulerService;
import com.muliamaulana.cataloguemovie.reminder.ReminderReceiver;

import java.util.Calendar;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private ReminderReceiver reminderReceiver = new ReminderReceiver();
    private static final String TAG = "Settings JobScheduler";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ValidFragment")
    public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,
            Preference.OnPreferenceChangeListener {

        String reminder_daily;
        String reminder_upcoming;
        String seting_localization;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_layout);

            reminder_daily = getActivity().getResources().getString(R.string.key_reminder_daily);
            reminder_upcoming = getActivity().getResources().getString(R.string.key_reminder_upcoming);
            seting_localization = getActivity().getResources().getString(R.string.key_setting_locale);

            findPreference(reminder_daily).setOnPreferenceChangeListener(this);
            findPreference(reminder_upcoming).setOnPreferenceChangeListener(this);
            findPreference(seting_localization).setOnPreferenceClickListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean isOn = (boolean) newValue;

            if (key.equals(reminder_daily)) {
                if (isOn) {
                    reminderReceiver.setDailyReminder(getApplicationContext());
                } else {
                    reminderReceiver.cancelDailyReminder(getApplicationContext());
                }
                Toast.makeText(SettingsActivity.this, getString(R.string.label_daily_reminder_title) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }

            if (key.equals(reminder_upcoming)) {
                if (isOn) {
                    startJobScheduler();
                } else stopJobScheduler();
                    Toast.makeText(SettingsActivity.this, getString(R.string.label_upcoming_reminder_title) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            if (key.equals(seting_localization)) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }
            return false;
        }
    }

    private void startJobScheduler() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        ComponentName scheduler = new ComponentName(this, JobSchedulerService.class);
        JobInfo builder = new JobInfo.Builder(10,scheduler)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(24*60*60*1000)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        int resulCode = jobScheduler.schedule(builder);
        if (resulCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    private void stopJobScheduler() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.cancel(10);
        Log.d(TAG,"Job Stopped");
    }
}