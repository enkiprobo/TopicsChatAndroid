package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegistrationSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);
    }

    public void keHalamanLogin(View view) {
        Intent inten = new Intent(this, LoginActivity.class);

        startActivity(inten);
        finish();
    }
}
