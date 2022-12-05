package com.kuderitest.mytestdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        onCreate() : Activity(화면)이 시작될 때 가장 먼저 실행 (안드로이드 엑티비티 생명주기)
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        }, 3000);
    }
}