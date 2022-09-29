package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DoctorsSchedule extends AppCompatActivity {
    Calendar myCalendar= Calendar.getInstance();
    TimePickerDialog picker;
    EditText editText,eText , endEText;
    Button saveSchedule;
    Doctor doctor;
    RecyclerView scheduleRecyV;
    ArrayList<Schedule> allSchedules;
    RecyclerView scheduleRv;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem itemswitch = menu.findItem(R.id.switch_action_bar);
        itemswitch.setActionView(R.layout.use_switch);


        final Switch sw = (Switch) menu.findItem(R.id.switch_action_bar).getActionView().findViewById(R.id.switch2);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });
        return true;
    }

    int scheduleYear=-1, scheduleDay=-1, scheduleMonth=-1,startHour=-1, startMinute=-1, endHour=-1, endMinute=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_schedule);
        Intent intent = getIntent();
        String doctorName= intent.getStringExtra("doctor");
        DoctorScheduleHelper doctorScheduleHelper= new DoctorScheduleHelper(getApplicationContext());


        if(doctorName.equals("mr B")){
            doctor=new Doctor("mr A","male","none", "mbbs","Dhaka", "1234567", R.drawable.img,35 );
        }
        if(doctorName.equals("mr A")){
            doctor=new Doctor("mr B","male","Bone", "fcps","Dhaka", "1234567", R.drawable.img_1,25 );
        }
        if(doctorName.equals("ms C")){
            doctor=new Doctor("ms C","female","Cardiac", "fcps","Chitagong", "1234567", R.drawable.img_2,50 );
        }



        scheduleRecyV= (RecyclerView) findViewById(R.id.scheduleRv);
        editText=(EditText) findViewById(R.id.date);
        eText=(EditText) findViewById(R.id.timeEt);
        eText.setInputType(InputType.TYPE_NULL);
        endEText=(EditText) findViewById(R.id.timeEtEnd);
        endEText.setInputType(InputType.TYPE_NULL);
        saveSchedule = (Button) findViewById(R.id.save);
        scheduleRecyV = (RecyclerView) findViewById(R.id.scheduleRv);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        doctor.schedules= doctorScheduleHelper.getAllSchedules(doctorName);

        DoctorScheduleAdapter doctorScheduleAdapter = new DoctorScheduleAdapter(getApplicationContext(),doctor.schedules);
        scheduleRecyV.setLayoutManager(linearLayoutManager);
        scheduleRecyV.setAdapter(doctorScheduleAdapter);


        saveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scheduleYear==-1 || startHour==-1 || endHour== -1){
                    Toast.makeText(getApplicationContext(), " enter all the entries", Toast.LENGTH_SHORT).show();
                    return;
                }
                double startTime = 0.0+ startHour + (1.0*startMinute/60);
                double endTime = 0.0+ (1.0*endMinute/60)+ endHour ;

                Schedule schedule = new Schedule(scheduleDay, scheduleMonth, scheduleYear,startTime,endTime);


                if(schedule.endHour-schedule.startHour < .09){
                    Toast.makeText(getApplicationContext(), "schedule should be at-least five minutes long", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!doctor.addToSchedule(schedule)){
                        Toast.makeText(getApplicationContext(), " conflicting schedule", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        doctorScheduleHelper.insertSchedule(doctor.name_, schedule);
                        Toast.makeText(getApplicationContext(), "schedule saved ", Toast.LENGTH_SHORT).show();
                        doctorScheduleAdapter.notifyDataSetChanged();
                    }
                }

            }
        });













        endEText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                picker = new TimePickerDialog(DoctorsSchedule.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                endEText.setText(sHour + ":" + sMinute);
                                endHour=sHour; endMinute = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                picker = new TimePickerDialog(DoctorsSchedule.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(sHour + ":" + sMinute);
                                startHour=sHour; startMinute = sMinute;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
                scheduleDay=day;
                scheduleMonth=month;
                scheduleYear= year;
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DoctorsSchedule.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }



}