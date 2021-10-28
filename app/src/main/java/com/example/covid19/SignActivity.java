package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.covid19.fragment.SignInFragment;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_sign, SignInFragment.newInstance()).commitNow();
        }
    }
}