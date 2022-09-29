package com.example.android_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoctorScheduleHelper extends SQLiteOpenHelper {
    Context context;
    String tablenName= "doctorsSchedule";
    public DoctorScheduleHelper(@Nullable Context context) {
        super(context, "doctorsSchedule.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ tablenName+ " ( name_ TEXT , day INTEGER , month INTEGER" +
                " , year INTEGER,  startHour REAL , endHour REAL, isFree INTEGER ) ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public void insertSchedule(String name_, Schedule schedule){
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("name_",name_);
        cv.put("day",schedule.day);
        cv.put("month",schedule.month);
        cv.put("year",schedule.year);
        cv.put("startHour",schedule.startHour);
        cv.put("endHour",schedule.endHour);
        cv.put("isFree",1);
        DB.insert(tablenName, null,cv);
    }
    public ArrayList<Schedule> getAllSchedules(String name_){
        SQLiteDatabase DB = getReadableDatabase();
        String query = "SELECT * FROM "+ tablenName + " WHERE name_ = ?";
        ArrayList<Schedule> schedules = new ArrayList<>();
        Cursor cursor = DB.rawQuery(query,new String[]{name_});

        if(cursor.moveToFirst()){
            do {
                int  day = cursor.getInt(1);
                int  month = cursor.getInt(2);
                int  year = cursor.getInt(3);
                double startHour= cursor.getDouble(4);
                double endHour= cursor.getDouble(5);
                int isFree = cursor.getInt(6);
                schedules.add(new Schedule(day,month,year,startHour,endHour,isFree==1));
            }while(cursor.moveToNext());
        }

        return  schedules;
    }
    public ArrayList<Schedule> getFreeSchedules(String name_){
        SQLiteDatabase DB = getReadableDatabase();
        String query = "SELECT * FROM "+ tablenName + " WHERE name_ = ?";
        ArrayList<Schedule> schedules = new ArrayList<>();
        Cursor cursor = DB.rawQuery(query,new String[]{name_});

        if(cursor.moveToFirst()){
            do {
                int  day = cursor.getInt(1);
                int  month = cursor.getInt(2);
                int  year = cursor.getInt(3);
                double startHour= cursor.getDouble(4);
                double endHour= cursor.getDouble(5);
                int isFree = cursor.getInt(6);
                if(isFree!=1){continue;}
                schedules.add(new Schedule(day,month,year,startHour,endHour));
            }while(cursor.moveToNext());
        }

        return  schedules;
    }
    public  void setBooked(Schedule schedule){
        if(!schedule.isFree){return;}
        double startHour= schedule.startHour;
        double endHour = schedule.endHour;
        int day= schedule.day;
        int month= schedule.month;
        int year= schedule.year;
        String doctor= schedule.doctor;

        ContentValues cv= new ContentValues();

        cv.put("name_",schedule.doctor);
        cv.put("day",schedule.day);
        cv.put("month",schedule.month);
        cv.put("year",schedule.year);
        cv.put("startHour",schedule.startHour);
        cv.put("endHour",schedule.endHour);
        cv.put("isFree",0);


        SQLiteDatabase db= getWritableDatabase();
        db.update(tablenName,
                cv,
                "name_  = ? AND   day   = ? AND   month   = ? AND   year   = ?AND   startHour   = ? AND   endHour   = ?",
                new String[]{doctor, String.valueOf(day), String.valueOf(month), String.valueOf(year), String.valueOf(startHour), String.valueOf(endHour)});

    }
    public ArrayList<Schedule> getFreeSchedulesAll(){
        SQLiteDatabase DB = getReadableDatabase();
        String query = "SELECT * FROM "+ tablenName ;
        ArrayList<Schedule> schedules = new ArrayList<>();
        Cursor cursor = DB.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                String doctor = cursor.getString(0);
                int  day = cursor.getInt(1);
                int  month = cursor.getInt(2);
                int  year = cursor.getInt(3);
                double startHour= cursor.getDouble(4);
                double endHour= cursor.getDouble(5);
                int isFree = cursor.getInt(6);
                if(isFree!=1){continue;}
                schedules.add(new Schedule(doctor,day,month,year,startHour,endHour,true));
            }while(cursor.moveToNext());
        }

        return  schedules;
    }
}