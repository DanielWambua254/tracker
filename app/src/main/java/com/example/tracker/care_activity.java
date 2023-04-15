package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class care_activity extends AppCompatActivity {

    LinearLayout todayBtn, calendarBtn, remindersBtn;
    Button menu;
    FloatingActionButton symptomsBtn;
    CardView adventureBtn, rainForestBtn, periodPainReliefBtn, footMassageBtn,
             lowerPainReliefBtn, neckPainReliefBtn, morningWarmUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        calendarBtn  =findViewById(R.id.profileBtn);
        remindersBtn = findViewById(R.id.settingsBtn);
        todayBtn  =findViewById(R.id.homeBtn);
        menu = findViewById(R.id.menu);
        adventureBtn = findViewById(R.id.forest_adventure_btn);
        rainForestBtn = findViewById(R.id.forest_rain_btn);
        periodPainReliefBtn = findViewById(R.id.period_pain_relief);
        footMassageBtn = findViewById(R.id.foot_massage);
        lowerPainReliefBtn = findViewById(R.id.lower_pain_relief);
        neckPainReliefBtn = findViewById(R.id.neck_pain_relief);
        morningWarmUpBtn = findViewById(R.id.morning_warm_up);
        symptomsBtn = findViewById(R.id.floatingActionBtn);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,account_activity.class);
                startActivity(intent);
            }
        });

        adventureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,rain_forest.class);
                startActivity(intent);
            }
        });

        rainForestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,adventure_activity.class);
                startActivity(intent);
            }
        });

        periodPainReliefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,selcare_tips.class);
                intent.putExtra("tittle",R.string.Period_pain_relief);
                intent.putExtra("content",R.string.period_tips);
                startActivity(intent);
            }
        });

        footMassageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,selcare_tips.class);
                intent.putExtra("tittle",R.string.foot_massage);
                intent.putExtra("content",R.string.foot_massage_tips);
                startActivity(intent);
            }
        });

        lowerPainReliefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,selcare_tips.class);
                intent.putExtra("tittle",R.string.lower_back_pain);
                intent.putExtra("content",R.string.lower_back_pain_tips);
                startActivity(intent);
            }
        });

        neckPainReliefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,selcare_tips.class);
                intent.putExtra("tittle",R.string.neck_pain);
                intent.putExtra("content",R.string.neck_pain_tips);
                startActivity(intent);
            }
        });

        morningWarmUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,selcare_tips.class);
                intent.putExtra("tittle",R.string.morning_warm_up);
                intent.putExtra("content",R.string.morning_warm_up_tips);
                startActivity(intent);
            }
        });


        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,calendar_activity.class);
                startActivity(intent);
                finish();
            }
        });

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,home_activity.class);
                startActivity(intent);
                finish();
            }
        });

        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,symptoms_activity.class);
                startActivity(intent);
            }
        });
        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,reminder_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}