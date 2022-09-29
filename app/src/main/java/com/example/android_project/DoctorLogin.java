package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorLogin extends AppCompatActivity {

    public  static ArrayList<Doctor> doctors;
    EditText et1,et2;
    CheckBox chck;
    Button btn1;

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
        setContentView(R.layout.activity_doctor_login);
         DoctorDbHelper doctorDbHelper = new DoctorDbHelper(getApplicationContext());
         doctors = doctorDbHelper.getAllDoctors();
         if(doctors.size()==0){
             doctors.add(new Doctor("mr A","male","none", "mbbs","Dhaka", "1234567", R.drawable.img,35 ));
             doctors.add(new Doctor("mr B","male","Bone", "fcps","Dhaka", "1234567", R.drawable.img_1,25 ));
             doctors.add(new Doctor("ms C","female","Cardiac", "fcps","Chitagong", "1234567", R.drawable.img_2,50 ));
            doctorDbHelper.addDoctors(doctors);
         }



        et1=(EditText) findViewById(R.id.username);
        et2=(EditText) findViewById(R.id.userpass);
        chck=(CheckBox) findViewById(R.id.logshowpass);
        btn1=(Button) findViewById(R.id.logbtn);

        chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    et2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    et2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctorName =  et1.getText().toString();
                String pass = et2.getText().toString();

                if(doctorName.equals("") || pass.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter All The Field", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result = doctorDbHelper.validateUsr(doctorName,pass);
                    if(result){
                        Intent intent1 = new Intent(getApplicationContext(),DoctorsSchedule.class);
                        intent1.putExtra("doctor", doctorName);
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid Info", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}