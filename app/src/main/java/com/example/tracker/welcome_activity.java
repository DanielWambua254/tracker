package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class welcome_activity extends AppCompatActivity {

    Button done;
    EditText cycle_length, period_length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        done = findViewById(R.id.done);
        cycle_length = findViewById(R.id.length_days_entry);
        period_length = findViewById(R.id.length_days_p_entry);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cycleLengthTXT = cycle_length.getText().toString();
                String periodLengthTXT = period_length.getText().toString();

                int cycleLength = Integer.parseInt(cycleLengthTXT);
                int periodLength = Integer.parseInt(periodLengthTXT);

                if (cycleLengthTXT.equals("") && periodLengthTXT.equals("") ) {
                    Toast.makeText(welcome_activity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (cycleLength < 28) {
                        Toast.makeText(welcome_activity.this, "Menstrual length cant be less than 28 days", Toast.LENGTH_SHORT).show();
                    } else if (cycleLength > 35) {
                        Toast.makeText(welcome_activity.this, "Menstrual length cant be more than 35 days", Toast.LENGTH_SHORT).show();
                    } else {
                        if (periodLength < 4) {
                            Toast.makeText(welcome_activity.this, "Period length cant be less than 4 days.", Toast.LENGTH_SHORT).show();
                        } else if (periodLength > 7) {
                            Toast.makeText(welcome_activity.this, "Period length cant be more than 7 days.", Toast.LENGTH_SHORT).show();
                        } else {
//                            if (password1.equals(password2)) {
//                                Intent intent = new Intent(welcome_activity.this,welcome_activity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(sign_up_activity.this, "Entered passwords do not match.", Toast.LENGTH_SHORT).show();
//                            }
                            Intent intent = new Intent(welcome_activity.this,home_activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

            }
        });
    }
}