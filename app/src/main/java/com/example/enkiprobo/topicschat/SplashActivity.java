package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import topicschat.networkutil.NetworkUtilTC;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences mPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPreference = getSharedPreferences(NetworkUtilTC.PREFERENCED_NAME, MODE_PRIVATE);
        String username = mPreference.getString("username", "-1");
        if (username.equals("-1")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }
            }, 3000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, UserMainActivity.class));
                    SplashActivity.this.finish();
                }
            }, 3000);
        }

    }
}