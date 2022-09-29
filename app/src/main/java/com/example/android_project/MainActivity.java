package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Notification;
import android.app.NotificationManager;
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

public class MainActivity extends AppCompatActivity {
    EditText username,password,repassword;
    CheckBox showpass;
    Button btn1,btn2,btn3;
    DBHelper myDB;
    Switch modeSwitch;
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
        setContentView(R.layout.activity_main);
        username=(EditText) findViewById(R.id.et1);
        password=(EditText) findViewById(R.id.et2);
        repassword=(EditText) findViewById(R.id.et3);
        showpass=(CheckBox) findViewById(R.id.showpass);
        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);
        myDB=new DBHelper(this);


        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String repass=password.getText().toString();
                if(user.equals("")|| pass.equals("")||repass.equals("")){
                    Toast.makeText(MainActivity.this, "Fill up all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                       Boolean checkresult=myDB.checkuser(user);
                       if(checkresult==false){
                         boolean result=myDB.insertData(user,pass);
                         if(result==true){
                             Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

//                            NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                            Notification notify=new Notification.Builder
//                                (getApplicationContext()).setContentTitle("booked schedule").setContentText("hi "+user).build();
//
//                            notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                            nm.notify(0, notify);
//                             Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
//                             startActivity(intent);
                         }
                         else{
                             Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                         }
                       }
                       else{
                           Toast.makeText(MainActivity.this, "User already exists!Please Sign in", Toast.LENGTH_SHORT).show();
                       }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Password isn't matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DoctorLogin.class);
                startActivity(intent);
            }
        });
    }
}