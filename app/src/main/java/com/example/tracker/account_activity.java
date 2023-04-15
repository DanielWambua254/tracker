package com.example.tracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class account_activity extends AppCompatActivity {

    Button backArrow, showBtn;
    TextView userNameTv, cycleLengthTv, periodLengthTv;
    String userNameTXT;
    DBHelper DB;

    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        backArrow = findViewById(R.id.back_arrow);
        userNameTv = findViewById(R.id.userNameTv);
        cycleLengthTv = findViewById(R.id.c_length_days_tv);
        periodLengthTv = findViewById(R.id.p_length_days_tv);
        showBtn = findViewById(R.id.doneBtn);
        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);
        String userNameTXT = sharedPreferences.getString(KEY_USERNAME, null);

        userNameTv.setText("Username: " + userNameTXT);

        // reading cycle length
        Cursor resCycle = DB.readCycleLength(userNameTXT);
        if (resCycle.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer cycleBuffer = new StringBuffer();

        while (resCycle.moveToNext()) {
            cycleBuffer.append(resCycle.getString(0)+"\n");
        }
        cycleLengthTv.setText(cycleBuffer.toString());

        // reading period length
        Cursor resPeriod = DB.readPeriodLength(userNameTXT);
        if (resPeriod.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer periodBuffer = new StringBuffer();

        while (resPeriod.moveToNext()) {
            periodBuffer.append(resPeriod.getString(0)+"\n");
        }
        periodLengthTv.setText(periodBuffer.toString());

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getSymptoms();
                if (res.getCount() == 0) {
                    Toast.makeText(account_activity.this, "You have not entered symptoms. !", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("Symptom: "+res.getString(0)+"\n");
                    buffer.append("Description: "+res.getString(1)+"\n");
                    buffer.append(" \n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(account_activity.this);
                builder.setCancelable(true);
                builder.setTitle("Your symptoms");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}