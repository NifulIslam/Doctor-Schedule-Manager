package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import java.util.ArrayList;

public class UserHomePage extends AppCompatActivity {

    Button firstDBtn,secondDBtn, thirdDBtn,shceduleBtn;
    String user;
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
        setContentView(R.layout.activity_user_home_page);
        FirstDoctor firstDoctor = new FirstDoctor();
        secondDoctor SecondDoctor = new secondDoctor();
        ThirdDoctor thirdDoctor = new ThirdDoctor();


        firstDBtn=findViewById(R.id.doctor1);
        secondDBtn=findViewById(R.id.doctor2);
        thirdDBtn=findViewById(R.id.doctor3);
        shceduleBtn=findViewById(R.id.schedulesBtn);

        FragmentManager fragmentManager = getSupportFragmentManager();

        shceduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookSchedule.class);
                startActivity(intent);
            }
        });

        firstDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.frameLayoutDoctors,firstDoctor).commit();
            }
        });
        secondDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.frameLayoutDoctors,SecondDoctor).commit();
            }
        });
        thirdDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.frameLayoutDoctors,thirdDoctor).commit();
            }
        });

    }
}