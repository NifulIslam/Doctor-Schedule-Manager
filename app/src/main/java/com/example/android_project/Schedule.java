package com.example.android_project;

import androidx.recyclerview.widget.RecyclerView;

public class Schedule  {
    public int day, month, year;
    public double startHour, endHour;
    boolean isFree;
    String doctor;

    public Schedule(int day, int month, int year, double startHour, double endHour) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isFree=true;
    }

    public Schedule(int day, int month, int year, double startHour, double endHour, boolean isFree) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isFree = isFree;
    }
    public Schedule(String doctor,int day, int month, int year, double startHour, double endHour, boolean isFree) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isFree = isFree;
        this.doctor=doctor;
    }
}
