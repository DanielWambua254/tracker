package com.example.tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context) {
        super(context, "trackerDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create TABLE userDetails (userName TEXT primary key, password TEXT)");
        DB.execSQL("create TABLE userDates (userName TEXT primary key, startDate TEXT, cycleLength TEXT, periodLength TEXT )");
        DB.execSQL("create TABLE symptoms (userName TEXT primary key, symptom TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop TABLE if exists userDetails");
        DB.execSQL("drop TABLE if exists userDates");
        DB.execSQL("drop TABLE if exists symptoms");
    }

    // register new user
    public boolean newUser(String userName, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password", password);

        Long result = DB.insert("userDetails", null, contentValues);

        return result != -1;
    }

    //new symptom
    public boolean newSymptom(String userName, String symptom) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("symptom", symptom);

        Long result = DB.insert("symptoms", null, contentValues);

        return result != -1;
    }

    //read symptoms
    public Cursor getSymptoms() {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from symptoms  ", null);
        return cursor;
    }

    //check if user exists in the database
    public boolean checkUser(String userName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userDetails where userName = ?",new  String[] {userName});
        return cursor.getCount() > 0;
    }

    //authenticate user
    public boolean authenticateUser(String userName, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userDetails where userName = ? and password = ?", new String[] {userName, password});
        return cursor.getCount() > 0;
    }

    // add user dates
    public boolean newUserDates(String userName, String startDate, String cycleLength, String periodLength) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("startDate", startDate);
        contentValues.put("cycleLength", cycleLength);
        contentValues.put("periodLength", periodLength);

        Long result = DB.insert("userDates", null, contentValues);

        return result != -1;
    }

    // read cycleLength
    public Cursor readCycleLength ( String userName) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select cycleLength from userDates where  userName = ?", new String[]{ userName});
        return cursor;
    }

    // read periodLength
    public Cursor readPeriodLength ( String userName) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select periodLength from userDates where  userName = ?", new String[]{ userName});
        return cursor;
    }

    //read start date
    public Cursor readStartDate ( String userName) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select startDate from userDates where  userName = ?", new String[]{ userName});
        return cursor;
    }

    public Boolean updateBalance (String userName, String password ) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        Cursor cursor = DB.rawQuery("select password from userDetails where userName = ?",
                new String[] {userName});

        if (cursor.getCount() > 0) {
            Long result = Long.valueOf(DB.update("userDetails", contentValues,
                    "userName = ?", new String[] {userName}));

            return result != 1;
        }
        else {
            return false;
        }
    }

}
