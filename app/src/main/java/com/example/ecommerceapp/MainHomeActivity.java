package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainHomeActivity extends AppCompatActivity {

    private Button goToSigninbtn,goToSignUpbtn;
    ImageView goToadminSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        goToSigninbtn = findViewById(R.id.go_to_signin);
        goToSignUpbtn = findViewById(R.id.go_to_signup);
        goToadminSignin = findViewById(R.id.go_to_adminSignin);

        Animation left= AnimationUtils.loadAnimation(this,R.anim.slide_left);
        Animation right= AnimationUtils.loadAnimation(this,R.anim.slide_right);

        goToSigninbtn.setAnimation(left);
        goToSignUpbtn.setAnimation(right);

        goToSigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainHomeActivity.this,Login.class);
                loginIntent.putExtra("fragment","signin");
                startActivity(loginIntent);
                finish();
            }
        });

        goToSignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainHomeActivity.this,Login.class);
                loginIntent.putExtra("fragment","signup");
                startActivity(loginIntent);
                finish();
            }
        });

        goToadminSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainHomeActivity.this,Login.class);
                loginIntent.putExtra("fragment","admin_signin");
                startActivity(loginIntent);
                finish();
            }
        });

    }
}
