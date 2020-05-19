package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.ecommerceapp.Admin.AdminActivities.addCatActivity;
import com.example.ecommerceapp.Admin.AdminHomeActivity;

public class MainActivity extends AppCompatActivity {

    public static  final String Shared_Prefs = "sharedPrefs";
    public static  final String Shared_Prefs2 = "sharedPrefs2";
    public static final String TEXT = "text";
    public static final String TEXT2 = "text";
    public static String username,main_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemClock.sleep(3000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();

        if(username.equals("NULL")){
            Intent loginIntent = new Intent(MainActivity.this,MainHomeActivity.class);
            startActivity(loginIntent);
            finish();
        }else if(username.equals("admin")){
            Intent loginIntent = new Intent(MainActivity.this, AdminHomeActivity.class);
            startActivity(loginIntent);
            finish();
        }else{
            Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        username = sharedPreferences.getString(TEXT,"NULL");
        main_email = sharedPreferences2.getString(TEXT2,"NULL");

    }



}
