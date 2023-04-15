package com.example.tracker;

import static com.example.tracker.care_activity.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class selcare_tips extends AppCompatActivity {

    Button backBtn;
    TextView tittleTv, contentTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selcare_tips);
        backBtn = findViewById(R.id.back);
        tittleTv = findViewById(R.id.tittleTv);
        contentTv = findViewById(R.id.contentTv);


        int tittleId = getIntent().getIntExtra("tittle",0 );
        String tittle = getString(tittleId);
        int contentId = getIntent().getIntExtra("content",0 );
        String content = getString(contentId);

        tittleTv.setText(tittle);
        contentTv.setText(content);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}