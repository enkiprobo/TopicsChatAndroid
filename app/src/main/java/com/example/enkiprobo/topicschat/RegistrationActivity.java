package com.example.enkiprobo.topicschat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import topicschat.networkutil.NetworkUtilTC;

public class RegistrationActivity extends AppCompatActivity {

    private EditText metUsername;
    private EditText metPassword;
    private EditText metPasswordConfirm;
    private EditText metFullname;

    private TextView mtvError;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        metUsername = (EditText) findViewById(R.id.et_registrationUsername);
        metPassword = (EditText) findViewById(R.id.et_registrationPassword);
        metPasswordConfirm = (EditText) findViewById(R.id.et_registrationConfirmPassword);
        metFullname = (EditText) findViewById(R.id.et_registrationFullName);

        mtvError = (TextView) findViewById(R.id.tv_errorMessage);
        btRegister = (Button) findViewById(R.id.bt_register);
    }

    public void keHalamanBerhasil(View view) {
//        Intent inten = new Intent(this, RegistrationSuccessActivity.class);
//
//        startActivity(inten);
//        finish();
        String username = metUsername.getText().toString();
        String password = metPassword.getText().toString();
        String passwordConfirm = metPasswordConfirm.getText().toString();
        String fullname = metFullname.getText().toString();

        mtvError.setText("");
        btRegister.setEnabled(false);
        btRegister.setBackgroundColor(getResources().getColor(R.color.black_overlay));
        if (username.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || fullname.length() == 0){
            mtvError.setText("form must not empty");
            mtvError.setVisibility(View.VISIBLE);
            btRegister.setEnabled(true);
            btRegister.setBackgroundColor(getResources().getColor(R.color.mainPurple));
            Log.d("REGISTRATION", "cek1");
        }else if (password.equals(passwordConfirm)){
            Log.d("REGISTRATION", "cek2");
            NetworkUtilTC networkUtilTC = new NetworkUtilTC();

            networkUtilTC.createUser(this, username, password, fullname, "kosong", "01-01-2001");
        } else{
            Log.d("REGISTRATION", "cek3");
            mtvError.setText("password didn't match");
            mtvError.setVisibility(View.VISIBLE);
            btRegister.setEnabled(true);
            btRegister.setBackgroundColor(getResources().getColor(R.color.mainPurple));
        }


    }
}
