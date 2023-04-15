package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class symptoms_activity extends AppCompatActivity {

    EditText cycle_length, description;
    Button done, cancelBtn, backBtn;
    DBHelper DB;
    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        done = findViewById(R.id.done);
        cycle_length = findViewById(R.id.length_days_entry);
        description = findViewById(R.id.description);
        cancelBtn = findViewById(R.id.edit_period);
        backBtn = findViewById(R.id.back);

        DB = new DBHelper(this);
        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);
        String userNameTXT = sharedPreferences.getString(KEY_USERNAME, null);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cycle_length.setText("");
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theSymptom = cycle_length.getText().toString();
                String theDescription = description.getText().toString();

               if (theSymptom.equals("")) {
                   Toast.makeText(symptoms_activity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
               } else {
                   Boolean newSymptom = DB.newSymptom(theSymptom,theDescription);
                   if (newSymptom) {
                       Toast.makeText(symptoms_activity.this, "Symptoms added successfully.", Toast.LENGTH_SHORT).show();

                   } else {
                       Toast.makeText(symptoms_activity.this, "Symptom added successfully!", Toast.LENGTH_SHORT).show();

                   }
               }
            }
        });
    }
}