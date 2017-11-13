package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void keHalamanUtama(View view) {
        Intent inten = new Intent(this, UserMainActivity.class);

        startActivity(inten);
        finish();
    }

    public void keHalamanRegistrasi(View view) {
        Intent inten = new Intent(this, RegistrationActivity.class);

        startActivity(inten);
    }
}
