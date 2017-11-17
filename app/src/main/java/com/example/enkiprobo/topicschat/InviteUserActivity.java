package com.example.enkiprobo.topicschat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import topicschat.adapters.UserGroupAdapter;
import topicschat.helper.DRYMethod;
import topicschat.networkutil.NetworkUtilTC;

public class InviteUserActivity extends AppCompatActivity {

    private Toolbar mtbInviteUser;
    private EditText metUsername;
    private TextView mtvInfo;
    private Button mbtInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);

        metUsername = (EditText) findViewById(R.id.et_inviteUsername);
        mtvInfo = (TextView) findViewById(R.id.tv_errorMessageInviteUser);
        mbtInvite = (Button) findViewById(R.id.bt_inviteUser);
        mtbInviteUser = (Toolbar) findViewById(R.id.tb_inviteUser);

        // resizing logo
        BitmapDrawable logoOri = (BitmapDrawable) getResources().getDrawable(R.drawable.logoextend);
        Bitmap logoBitResize = Bitmap.createScaledBitmap(logoOri.getBitmap(), 350,100, false);
        Drawable logoResize = new BitmapDrawable(getResources(), logoBitResize);

        mtbInviteUser.setLogo(logoResize);
        mtbInviteUser.setBackgroundColor(getResources().getColor(R.color.mainPurple));
        setSupportActionBar(mtbInviteUser);

        Log.d("MAJU", "apakah sudah sampai sini?");
    }

    public void menginviteUser(View view) {
        DRYMethod.buttonClicked(mbtInvite);

        mtvInfo.setText(getResources().getString(R.string.please_wait));
        mtvInfo.setVisibility(View.VISIBLE);

        String username = metUsername.getText().toString();
        if (username.length() > 0){
            int idGroup = getIntent().getIntExtra(UserGroupAdapter.EXTRA_IDGROUP, -1);
            new NetworkUtilTC().insertMember(this, idGroup, username);
        } else {
            mtvInfo.setText("please fill all form");
            DRYMethod.buttonToRiple(mbtInvite);
        }
    }
}
