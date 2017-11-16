package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import topicschat.helper.DRYMethod;
import topicschat.networkutil.NetworkUtilTC;

public class LoginActivity extends AppCompatActivity {

    private EditText metUsername;
    private EditText metPassword;
    private TextView mtvErrorLogin;
    private Button mbtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        metUsername = (EditText) findViewById(R.id.et_usernameLogin);
        metPassword = (EditText) findViewById(R.id.et_passwordLogin);
        mtvErrorLogin = (TextView) findViewById(R.id.tv_errorLogin);
        mbtLogin = (Button) findViewById(R.id.bt_login);
    }

    public void keHalamanUtama(View view) {
//        Intent inten = new Intent(this, UserMainActivity.class);
//
//        startActivity(inten);
//        finish();

        mtvErrorLogin.setVisibility(View.GONE);
        DRYMethod.buttonClicked(mbtLogin);

        String username = metUsername.getText().toString();
        String password = metPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            mtvErrorLogin.setVisibility(View.VISIBLE);
            mtvErrorLogin.setText("please fill all form");

            DRYMethod.buttonToRiple(mbtLogin);
        } else {
            NetworkUtilTC networkUtilTC = new NetworkUtilTC();
            networkUtilTC.Login(this, username, password);
        }
    }

    public void keHalamanRegistrasi(View view) {
        Intent inten = new Intent(this, RegistrationActivity.class);

        startActivity(inten);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mtvErrorLogin.setVisibility(View.GONE);
    }
}
