package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.covid19.session.SessionManagerUtil;

import java.util.Calendar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        if (!isAllowed){
            Intent intent = new Intent(this, SignActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onResume();
    }
}