package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class welcome_activity extends AppCompatActivity {

    Button done, datePickerBtn, cancelBtn;
    DatePickerDialog datePickerDialog;
    EditText cycle_length, period_length;
    public String selectedDate;
    DBHelper DB;

    public  static final String EXTRA_TEXT2 = "com.example.tracker.EXTRA_TEXT2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        done = findViewById(R.id.done);
        cycle_length = findViewById(R.id.length_days_entry);
        period_length = findViewById(R.id.length_days_p_entry);
        datePickerBtn = findViewById(R.id.tap_to_set);
        cancelBtn = findViewById(R.id.edit_period);
        DB = new DBHelper(this);

        datePickerBtn.setText(selectedDate());

        Intent intent2 = getIntent();
        String myUserName = intent2.getStringExtra(sign_up_activity.EXTRA_TEXT);

        initDatePicker();
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cycle_length.setText("");
                period_length.setText("");
            }
        });
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
                            Boolean addUserDates = DB.newUserDates(myUserName,selectedDate,cycleLengthTXT,periodLengthTXT);

                            if (addUserDates) {
                                Toast.makeText(welcome_activity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(welcome_activity.this,MainActivity.class);
                                // passing this data to next activity
                                intent.putExtra(EXTRA_TEXT2, myUserName);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(welcome_activity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }

            }
        });
    }

    private String selectedDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datePickerBtn.setText(date);
                selectedDate = date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);


    }
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}