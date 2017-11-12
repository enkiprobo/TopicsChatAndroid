package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void keHalamanBerhasil(View view) {
        Intent inten = new Intent(this, RegistrationSuccessActivity.class);

        startActivity(inten);
    }
}
