package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import topicschat.adapters.UserGroupAdapter;
import topicschat.sqlitedatamodel.UsersGroup;

public class UserMainActivity extends AppCompatActivity {

    private RecyclerView mrvUserGroup;
    private TextView mtvInfoMain;
    private List<UsersGroup> usersGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        // resizing logo
        BitmapDrawable logoOri = (BitmapDrawable) getResources().getDrawable(R.drawable.topics_chat_2xxxhdpi);
        Bitmap logoBitResize = Bitmap.createScaledBitmap(logoOri.getBitmap(), 450, 100, false);
        Drawable logoResize = new BitmapDrawable(getResources(), logoBitResize);

        // set app bar
        Toolbar mTB = (Toolbar) findViewById(R.id.tb_userMain);
        mTB.setLogo(logoResize);

        setSupportActionBar(mTB);
//        Log.d("JALAN", "berhasil dibuat");

        // hapus ini nanti ====
        UsersGroup gr = new UsersGroup(1, 1, "Naruto", "kosong", "hai ganteng...", "00:00", 15);
        gr.save();
        //==================
        mtvInfoMain = (TextView) findViewById(R.id.tv_infoMain);
        mrvUserGroup = (RecyclerView) findViewById(R.id.rv_userGroup);
        usersGroupList = UsersGroup.listAll(UsersGroup.class);

        if (usersGroupList.size() < 1) {
            mtvInfoMain.setText(getResources().getString(R.string.no_group));
        } else {
            Log.d("USERMAIN", "jumlah ," + usersGroupList.size());
            mtvInfoMain.setVisibility(View.GONE);
            mrvUserGroup.setVisibility(View.VISIBLE);
        }
        UserGroupAdapter adapter = new UserGroupAdapter(this, usersGroupList);
        mrvUserGroup.setAdapter(adapter);
        mrvUserGroup.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("JALAN", "berhasil dipencet");
        switch (item.getItemId()) {
            case R.id.i_createNewGroup:
                Intent inten = new Intent(this, NewGroupActivity.class);

                startActivity(inten);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void keHalamanGroup(View view) {
        Intent inten = new Intent(this, GroupChatActivity.class);

        startActivity(inten);
    }
}
