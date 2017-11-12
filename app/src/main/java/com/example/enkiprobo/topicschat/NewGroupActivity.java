package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NewGroupActivity extends AppCompatActivity {

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
    }

    public void mendaftarkanGroup(View view) {
        Intent inten = new Intent(this, UserMainActivity.class);

        startActivity(inten);
        finish();
    }
}
