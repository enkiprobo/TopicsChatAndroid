package com.example.enkiprobo.topicschat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import topicschat.networkutil.NetworkUtilTC;

public class NewGroupActivity extends AppCompatActivity {

    private TextView mtvInfo;
    private Button mbtCreateGroup;
    private EditText metGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        // resizing logo
        BitmapDrawable logoOri = (BitmapDrawable) getResources().getDrawable(R.drawable.topics_chat_2xxxhdpi);
        Bitmap logoBitResize = Bitmap.createScaledBitmap(logoOri.getBitmap(), 450,100, false);
        Drawable logoResize = new BitmapDrawable(getResources(), logoBitResize);

        Toolbar mTBCG = (Toolbar) findViewById(R.id.tb_createGroup);
        mTBCG.setLogo(logoResize);

        setSupportActionBar(mTBCG);

        mtvInfo = (TextView) findViewById(R.id.tv_errorMessageCreateGroup);
        mbtCreateGroup = (Button) findViewById(R.id.bt_createGroup);
        metGroupName = (EditText) findViewById(R.id.et_createGroupName);
    }

    public void mendaftarkanGroup(View view) {
//        Intent inten = new Intent(this, UserMainActivity.class);
//
//        startActivity(inten);
//        finish();

        mbtCreateGroup.setEnabled(false);
        mbtCreateGroup.setBackgroundColor(getResources().getColor(R.color.black_overlay));

        mtvInfo.setText(getResources().getString(R.string.please_wait));
        mtvInfo.setVisibility(View.VISIBLE);

        String groupName = metGroupName.getText().toString();
        if (groupName.length() > 0){
            String username = getSharedPreferences(NetworkUtilTC.PREFERENCED_NAME, MODE_PRIVATE).getString("username","");
            new NetworkUtilTC().createGroup(this, groupName, "", username);
        } else {
            mtvInfo.setText("please fill all form");
            mbtCreateGroup.setEnabled(true);
            mbtCreateGroup.setBackgroundColor(getResources().getColor(R.color.mainPurple));
        }

    }
}
