package com.example.tracker;

import static android.provider.Settings.System.DATE_FORMAT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class home_activity extends AppCompatActivity {

    Button menu;
    FloatingActionButton symptomsBtn;
    LinearLayout calendarBtn, careBtn, remindersBtn, changePass;
    TextView usernameTv, daysTV, remainingDaysTv, pregnancyTv;
    String cycleLength, startDate, startDate2, periodLength, futureDate;

    String myLength = "0";

    float remainingDays;
    DBHelper DB;
    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";
    public static final String REMAINING_DAYS = "REMAINING_DAYS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu = findViewById(R.id.menu);
        calendarBtn = findViewById(R.id.calendarBtn);
        careBtn  =findViewById(R.id.supportBtn);
        usernameTv = findViewById(R.id.userNameTxt);
        daysTV = findViewById(R.id.days);
        remindersBtn = findViewById(R.id.settingsBtn);
        changePass = findViewById(R.id.changePass);
        remainingDaysTv = findViewById(R.id.remainingDays);
        pregnancyTv = findViewById(R.id.pEndsBtn);
        symptomsBtn = findViewById(R.id.floatingActionBtn);


        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);
        String userNameTXT = sharedPreferences.getString(KEY_USERNAME, null);

        usernameTv.setText(userNameTXT);

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
        cycleLength = cycleBuffer.toString();

        myLength = cycleLength.trim();

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
        periodLength = periodBuffer.toString();

        // reading start date
        Cursor reStartDate = DB.readStartDate(userNameTXT);
        if (reStartDate.getCount()  == 0) {
            Toast.makeText(this, "brr", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer dateBuffer = new StringBuffer();

        while (reStartDate.moveToNext()) {
            dateBuffer.append(reStartDate.getString(0)+"\n");
        }
        startDate = dateBuffer.toString();
        startDate2 = convertDate(startDate);

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String currentDate = formatter.format(date);
        String currentDate2 = currentDate.trim();
        futureDate = myFutureDate(startDate2,myLength);
        // convert future date
        String stringdate1 = futureDate;
        String futureDate2 = "";
        try {

            SimpleDateFormat format1 = new SimpleDateFormat("MM dd yyyy");
            Date date1 = format1.parse(stringdate1);
            SimpleDateFormat format2 = new SimpleDateFormat("MM-dd-yyyy");

            futureDate2 = format2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        remainingDays = daysBetweenDates(currentDate2, futureDate2);
        int days = Math.round(remainingDays);
        String val = String.valueOf(days);
        daysTV.setText(val);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(REMAINING_DAYS, days);
        editor.apply();

        int ovulationDays = days -3;
        if (ovulationDays < 3) {
            remainingDaysTv.setText("Ovulation: "+0+" days remaining");
            pregnancyTv.setText("Chances of getting pregnant: Low");
        } else {
            remainingDaysTv.setText("Ovulation: " + ovulationDays + " days remaining");
            pregnancyTv.setText("Chances of getting pregnant: High");
        }
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,change_pass_activity.class);
                startActivity(intent);
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,calendar_activity.class);
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,account_activity.class);
                startActivity(intent);
            }
        });

        symptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,symptoms_activity.class);
                startActivity(intent);
            }
        });
        careBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,care_activity.class);
                startActivity(intent);
            }
        });

        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_activity.this,reminder_activity.class);
                startActivity(intent);
            }
        });
    }

    private String convertDate(String date) {
        String month, sub2;
        month = date.substring(0,3);
        sub2 = date.substring(4);
        if(month.equals("JAN"))
            return "1 "+sub2;
        if(month.equals("FEB"))
            return "2"+sub2;
        if(month.equals("MAR"))
            return "3 "+sub2;
        if(month.equals("APR"))
            return "4 "+sub2;
        if(month.equals("MAY"))
            return "5 "+sub2;
        if(month.equals("JUN"))
            return "6 "+sub2;
        if(month.equals("JUL"))
            return "7 "+sub2;
        if(month.equals("AUG"))
            return "8 "+sub2;
        if(month.equals("SEP"))
            return "9 "+sub2;
        if(month.equals("OCT"))
            return "10 "+sub2;
        if(month.equals("NOV"))
            return "11"+sub2;
        if(month.equals("DEC"))
            return "12 "+sub2;

        //default should never happen
        return "JAN";
    }

    private static String myFutureDate(String date, String days) {
        String DATE_FORMAT = "MM dd yyyy";
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        int days2  = Integer.parseInt(days);
        try {
            Date myDate = df.parse(date.trim());
            c.setTime(myDate);
            c.add(Calendar.DATE,days2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String toDate = df.format(c.getTime());

        return toDate;
    }

    public float daysBetweenDates(String date1, String date2) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        final LocalDate firstDate = LocalDate.parse(date1, formatter);
        final LocalDate secondDate = LocalDate.parse(date2, formatter);
        final float days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
}