package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class Login extends AppCompatActivity {
    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frameLayout = findViewById(R.id.registerFrameLayout);
        String fragment_name = getIntent().getStringExtra("fragment");

        if(fragment_name.equals("signin")){
            setFragment(new SignInFragment());
        }else if(fragment_name.equals("signup")){
            setFragment(new SignUpFragment());
        }else{
            setFragment(new AdminSignInFragment());
        }



    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
