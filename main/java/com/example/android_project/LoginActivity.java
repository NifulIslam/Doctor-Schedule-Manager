package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText et1,et2;
    CheckBox chck;
    Button btn1;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1=(EditText) findViewById(R.id.username);
        et2=(EditText) findViewById(R.id.userpass);
        chck=(CheckBox) findViewById(R.id.logshowpass);
        btn1=(Button) findViewById(R.id.logbtn);

        myDB = new DBHelper(this);
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
                String user =  et1.getText().toString();
                String pass = et2.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please Enter All The Field", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result = myDB.checkuserpass(user,pass);
                    if(result==true){
                        Intent intent1 = new Intent(getApplicationContext(),InfoActivity.class);
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Info", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
  }
}