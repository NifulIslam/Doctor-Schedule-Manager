package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

public class BookSchedule extends AppCompatActivity {

    ArrayList<Schedule> freeSchedules;
    RecyclerView recyclerViewSchedules;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        DoctorScheduleHelper doctorScheduleHelper = new DoctorScheduleHelper(getApplicationContext());
        freeSchedules = doctorScheduleHelper.getFreeSchedulesAll();
        recyclerViewSchedules = findViewById(R.id.scheduleRvPatient);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        SchedulePatientAdapter schedulePatientAdapter = new SchedulePatientAdapter(getApplicationContext(),freeSchedules);
        recyclerViewSchedules.setLayoutManager(linearLayoutManager);
        recyclerViewSchedules.setAdapter(schedulePatientAdapter);
    }
}