package com.example.android_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //public static final String USERS = "users";
    //public static final String COLUMN_USERS_PASSWORD = "Password";
    //public static final String COLUMN_USERS_NAME = "NAME";
    public DBHelper(Context context) {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
         myDB.execSQL("create Table users(username Text primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
          myDB.execSQL("drop Table if exists users");
    }
    public boolean insertData(String username,String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        long result= myDB.insert("users",null,cv);

        if(result==-1){
            return false;
        }
        else{
            return true;
          }
        }
        public boolean checkuser(String username){
          SQLiteDatabase myDB=this.getWritableDatabase();
          Cursor cursor=myDB.rawQuery("select * from users where username = ?",new String[]{username});
          if(cursor.getCount()>0){
              return true;
          }
          else{
              return false;
          }
        }

        public boolean checkuserpass(String username,String password){
            SQLiteDatabase myDB=this.getWritableDatabase();
            Cursor cursor=myDB.rawQuery("select * from users where username = ? and password = ?",new String[]{username,password});

            if(cursor.getCount()>0){
                return true;
            }
            else{
                return false;
            }
        }
    }
