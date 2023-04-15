package com.example.tracker;

import static com.example.tracker.home_activity.REMAINING_DAYS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tracker.databinding.ActivityReminderBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Calendar;

public class reminder_activity extends AppCompatActivity {
    private ActivityReminderBinding binding;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Button menuBtn;
    FloatingActionButton symptomsBtn;
    private int notificationId = 1;
    LinearLayout calendarBtn, careBtn, homeBtn;
    Switch periodSwitch;
    Switch ovulationSwitch;
    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";
    public static final String periodSwitchVal = "true";
    public static final String ovulationSwitchVal = "true";
    public  int days;
    Button saveBtn;
    private boolean periodSwitchValOnOff, ovulationSwitchValOnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_reminder);
        menuBtn = findViewById(R.id.back);
        calendarBtn = findViewById(R.id.calendarBtn);
        careBtn  =findViewById(R.id.supportBtn);
        homeBtn = findViewById(R.id.homeBtn);
        periodSwitch = findViewById(R.id.periodSwitch);
        ovulationSwitch = findViewById(R.id.ovulationSwitch);
        saveBtn = findViewById(R.id.doneBtn);
        symptomsBtn = findViewById(R.id.floatingActionBtn);

        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);
        days = sharedPreferences.getInt(REMAINING_DAYS, 2);

        String val = String.valueOf(days);
        createNotificationChannel();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,6);
        calendar.set(Calendar.MINUTE,22);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        loadData();
        updateView();

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reminder_activity.this,calendar_activity.class);
                startActivity(intent);
                finish();
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reminder_activity.this,account_activity.class);
                startActivity(intent);
            }
        });

        careBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reminder_activity.this,care_activity.class);
                startActivity(intent);
                finish();
            }
        });

        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reminder_activity.this,symptoms_activity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reminder_activity.this,home_activity.class);
                startActivity(intent);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSate();
                alarms();
            }
        });

    }

    private void alarms() {
        if (ovulationSwitch.isChecked()) {
            if (days < 2) {
                setAlarm();
            }
            Toast.makeText(this, "Alarm set!", Toast.LENGTH_SHORT).show();
        } else {
            cancelAlarm();
        }
    }

    private void saveSate() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_USERNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(periodSwitchVal,periodSwitch.isChecked());
        editor.putBoolean(ovulationSwitchVal,ovulationSwitch.isChecked());
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_USERNAME, MODE_PRIVATE);
        periodSwitchValOnOff = sharedPreferences.getBoolean(periodSwitchVal,true);
        ovulationSwitchValOnOff = sharedPreferences.getBoolean(ovulationSwitchVal, true);
    }

    private void updateView() {
        periodSwitch.setChecked(periodSwitchValOnOff);
        ovulationSwitch.setChecked(ovulationSwitchValOnOff);
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent  = PendingIntent.getBroadcast(this,0,intent,0);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm cancelled!", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent  = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);


    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "myNotificationChannel";
            String description = "This channel is for tis app";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notification",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}