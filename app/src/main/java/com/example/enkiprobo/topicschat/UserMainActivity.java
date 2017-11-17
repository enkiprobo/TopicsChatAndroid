package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import topicschat.adapters.UserGroupAdapter;
import topicschat.helper.TPConstant;
import topicschat.networkutil.NetworkUtilTC;
import topicschat.networkutil.WebsocketUtilTC;
import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;
import topicschat.sqlitedatamodel.Mute;
import topicschat.sqlitedatamodel.UsersGroup;

public class UserMainActivity extends AppCompatActivity {

    private DrawerLayout mdlUserGroup;
    private NavigationView mnvUserGroup;
    private RecyclerView mrvUserGroup;
    private TextView mtvInfoMain;
    private List<UsersGroup> usersGroupList;
    private SharedPreferences mPreference;
    private TextView mName;

    private String userUsername;
    private String userFullname;
    private String userProfileImage;
    private String userBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_drawer);

        mPreference = getSharedPreferences(TPConstant.PREFERENCED_FILE_NAME, MODE_PRIVATE);
        mdlUserGroup = (DrawerLayout) findViewById(R.id.dl_usersGroup);
        mtvInfoMain = (TextView) findViewById(R.id.tv_infoMain);
        mrvUserGroup = (RecyclerView) findViewById(R.id.rv_userGroup);
        mnvUserGroup = (NavigationView) findViewById(R.id.nv_usersGroup);

        userFullname = mPreference.getString(TPConstant.PREFERENCED_FULLNAME, "");
        userUsername = mPreference.getString(TPConstant.PREFERENCED_USERNAME, "");
        userBirthDate = mPreference.getString(TPConstant.PREFERENCED_USERBIRTH, "");
        userProfileImage = mPreference.getString(TPConstant.PREFERENCED_USERIMAGE, "");

        // resizing logo
        BitmapDrawable logoOri = (BitmapDrawable) getResources().getDrawable(R.drawable.logoextend);
        Bitmap logoBitResize = Bitmap.createScaledBitmap(logoOri.getBitmap(), 350, 100, false);
        Drawable logoResize = new BitmapDrawable(getResources(), logoBitResize);

        // set app bar
        Toolbar mTB = (Toolbar) findViewById(R.id.tb_userMain);
        mTB.setLogo(logoResize);

        setSupportActionBar(mTB);
//        Log.d("JALAN", "berhasil dibuat");

        // hapus ini nanti ====
//        UsersGroup gr = new UsersGroup(1, 1, "Naruto", "kosong", "hai ganteng...", "00:00", 15);
//        gr.save();
//        //==================


        // get group
        usersGroupList = UsersGroup.listAll(UsersGroup.class);
        if (usersGroupList.size() < 1) {
            new NetworkUtilTC().getUserGroup(this, mPreference.getString("username", ""));
        } else {
            Log.d("USERMAIN", "jumlah ," + usersGroupList.size());
            mtvInfoMain.setVisibility(View.GONE);
            mrvUserGroup.setVisibility(View.VISIBLE);
        }

        UserGroupAdapter adapter = new UserGroupAdapter(this, usersGroupList);
        mrvUserGroup.setAdapter(adapter);
        mrvUserGroup.setLayoutManager(new LinearLayoutManager(this));

        // untuk logout
        mnvUserGroup.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.i_navLogout:
                        UserMainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ChatDetail.deleteAll(ChatDetail.class);
                                GroupsTopic.deleteAll(GroupsTopic.class);
                                Mute.deleteAll(Mute.class);
                                UsersGroup.deleteAll(UsersGroup.class);

                                SharedPreferences sp = UserMainActivity.this.getSharedPreferences(NetworkUtilTC.PREFERENCED_NAME, UserMainActivity.this.MODE_PRIVATE);
                                SharedPreferences.Editor spe = sp.edit();
                                spe.clear();
                                spe.apply();

                                if (WebsocketUtilTC.wss != null){
                                    Log.d("WEBSOCKETOKHTTP", "ingin menutup koneksi");
                                    WebsocketUtilTC.wss.close(1000,"logout");
                                    Log.d("WEBSOCKETOKHTTP", "berhasil menutup koneksi");
                                    WebsocketUtilTC.wss = null;
                                }
                                Intent inten = new Intent(UserMainActivity.this, LoginActivity.class);
                                UserMainActivity.this.startActivity(inten);
                                UserMainActivity.this.finish();
                            }
                        });
                        return true;
                    default:
                        return true;
                }
            }
        });
        mName = (TextView) mnvUserGroup.getHeaderView(0).findViewById(R.id.tv_navHeaderFullName);
        mName.setText(userFullname);

        if (Mute.count(Mute.class)<1){
            new NetworkUtilTC().getAllMute(userUsername);
        }
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
            case R.id.i_setting:
                if(mdlUserGroup.isDrawerOpen(Gravity.START)){
                    mdlUserGroup.closeDrawer(Gravity.END);
                } else{
                    mdlUserGroup.openDrawer(Gravity.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        UserGroupAdapter uga = (UserGroupAdapter)mrvUserGroup.getAdapter();
        uga.setUsersGroupList(UsersGroup.listAll(UsersGroup.class));
        uga.notifyDataSetChanged();
    }

    public void keHalamanGroup(View view) {
        Intent inten = new Intent(this, GroupChatActivity.class);

        startActivity(inten);
    }
}
