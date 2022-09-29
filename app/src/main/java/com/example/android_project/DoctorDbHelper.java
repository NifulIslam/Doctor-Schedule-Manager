package com.example.android_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoctorDbHelper extends SQLiteOpenHelper {
    Context context;
    String tablenName= "doctors";
    public DoctorDbHelper(@Nullable Context context) {
        super(context, "doctors.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ tablenName+ " ( name_ TEXT PRIMARY KEY , gender TEXT ,  specialization TEXT, " +
                " degree TEXT , location TEXT , password TEXT , photo INTEGER , age INTEGER ) ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public ArrayList<Doctor> getAllDoctors(){
        ArrayList<Doctor> doctors = new ArrayList<>();
        String query  = "SELECT * FROM " + tablenName;
        SQLiteDatabase DB = getReadableDatabase();
        Cursor cursor = DB.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {

                String name_ = cursor.getString(0);
                String gender = cursor.getString(1);
                String specialization = cursor.getString(2);
                String degree = cursor.getString(3);
                String location = cursor.getString(4);
                String password = cursor.getString(5);
                int photo_ = cursor.getInt(6);
                int age = cursor.getInt(7);
                doctors.add(new Doctor(name_, gender,specialization, degree,location, password, photo_, age));


            }while(cursor.moveToNext());
        }

        return  doctors;
    }

    public void addDoctors( ArrayList<Doctor> doctors){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Doctor doctor : doctors){
            ContentValues cv = new ContentValues();
            cv.put("name_", doctor.name_);
            cv.put("gender",doctor.gender);
            cv.put("specialization",doctor.specialization);
            cv.put("degree", doctor.degree);
            cv.put("location", doctor.location);
            cv.put("password", doctor.password);
            cv.put("photo", doctor.photo);
            cv.put("age", doctor.age);

            db.insert(tablenName,null,cv);

        }

    }
    public boolean validateUsr(String username,String password){
        SQLiteDatabase myDB=this.getReadableDatabase();
        Cursor cursor=myDB.rawQuery("select * from "+ tablenName +" where name_ = ? and password = ?",new String[]{username,password});

        return cursor.getCount()>0 ;

    }
}
