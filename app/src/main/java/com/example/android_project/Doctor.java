package com.example.android_project;

import java.util.ArrayList;

public class Doctor {
    public String name_, gender, specialization, degree , location,password;
    public int age, photo;

    public Doctor(String name_, String gender, String specialization, String degree, String location, String password, int photo, int age) {
        this.name_ = name_;
        this.gender = gender;
        this.specialization = specialization;
        this.degree = degree;
        this.location = location;
        this.password = password;
        this.photo = photo;
        this.age = age;
    }

    public ArrayList<Schedule> schedules = new ArrayList<>();
    public boolean addToSchedule(Schedule schedule){
        for(Schedule sc: schedules){
            double intervalStart= Math.max(sc.startHour, schedule.startHour);
            double intervalEnd= Math.min(sc.endHour,schedule.endHour);
            if(sc.day==schedule.day && sc.month== schedule.month && sc.year== schedule.year && intervalStart<intervalEnd ){
             return false;
            }

        }

        schedules.add(schedule);
        return true;
    }
}
