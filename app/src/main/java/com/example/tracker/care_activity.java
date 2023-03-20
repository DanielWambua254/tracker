package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class care_activity extends AppCompatActivity {

    LinearLayout todayBtn, calendarBtn;
    Button menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        calendarBtn  =findViewById(R.id.profileBtn);
        todayBtn  =findViewById(R.id.homeBtn);
        menu = findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(care_activity.this,account_activity.class);
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
    }
}