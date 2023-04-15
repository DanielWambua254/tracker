package com.example.tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class calendar_activity extends AppCompatActivity implements calender_adapter.onItemListener{

    private TextView monthYear;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectDate;

    private Button nextMonth, prevMonth, menu;
    FloatingActionButton symptomsBtn;
    LinearLayout todayBtn, careBtn, remindersBtn;
    DBHelper DB;
    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        widgets();

        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);
        String userNameTXT = sharedPreferences.getString(KEY_USERNAME, null);


        selectDate = LocalDate.now();
        monthYear.setText(monthDate(selectDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectDate);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calender_adapter myCalender_adapter = new calender_adapter(daysInMonth, this);
        calendarRecyclerView.setAdapter(myCalender_adapter);

        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevMonth();
            }
        });

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonth();
            }
        });

        careBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_activity.this,care_activity.class);
                startActivity(intent);
                finish();
            }
        });

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_activity.this,home_activity.class);
                startActivity(intent);
                finish();
            }
        });

        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_activity.this,symptoms_activity.class);
                startActivity(intent);
            }
        });
        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_activity.this,reminder_activity.class);
                startActivity(intent);
                finish();
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_activity.this,account_activity.class);
                startActivity(intent);
            }
        });


    }


    private void widgets() {
        monthYear = findViewById(R.id.month_tv);
        calendarRecyclerView = findViewById(R.id.calendarView);
        nextMonth = findViewById(R.id.next_month);
        prevMonth = findViewById(R.id.prev_month);
        careBtn  =findViewById(R.id.supportBtn);
        todayBtn  =findViewById(R.id.homeBtn);
        menu = findViewById(R.id.menu);
        remindersBtn = findViewById(R.id.settingsBtn);
        symptomsBtn = findViewById(R.id.floatingActionBtn);
    }
    private void setMonthView() {
        monthYear.setText(monthDate(selectDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectDate);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calender_adapter myCalender_adapter = new calender_adapter(daysInMonth, this);
        calendarRecyclerView.setAdapter(myCalender_adapter);
    }


    private ArrayList<String> daysInMonthArray(LocalDate selectDate) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth  = YearMonth.from(selectDate);
        int dayInMonth = yearMonth.lengthOfMonth();


        LocalDate firstOfMonth = selectDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > dayInMonth + dayOfWeek ) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }


    private String monthDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter ;
        dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(dateTimeFormatter);
    }


    @Override
    public void onItemClick(int position, String dayText) {

        if (!dayText.equals("")) {
            String msg = "Selected date"+ dayText;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    private void prevMonth() {
        selectDate = selectDate.minusMonths(1);
        setMonthView();
    }


    private void nextMonth() {
        selectDate = selectDate.plusMonths(1);
        setMonthView();
    }
}