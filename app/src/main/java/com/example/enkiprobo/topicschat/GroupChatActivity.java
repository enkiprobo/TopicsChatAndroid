package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import topicschat.adapters.UserGroupAdapter;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mtbGroupChat;
    private ImageView mivChatTopic;
    private EditText metChatMessage;
    private Button mbtSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        mtbGroupChat = (Toolbar) findViewById(R.id.tb_chatGroup);
        mivChatTopic = (ImageView) findViewById(R.id.iv_chatTopics);
        metChatMessage = (EditText) findViewById(R.id.et_chatMessage);
        mbtSend = (Button) findViewById(R.id.bt_chatSend);

        setSupportActionBar(mtbGroupChat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_chat_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.i_inviteUser:
                Intent inten = getIntent();
                int idGroup = inten.getIntExtra(UserGroupAdapter.EXTRA_IDGROUP, -1);

                Intent intentnew = new Intent(this, InviteUserActivity.class);
                intentnew.putExtra(UserGroupAdapter.EXTRA_IDGROUP, idGroup);
                startActivity(intentnew);
                Log.d("MAJU", "bisa gak ada masalah?");
                return true;
            case R.id.i_topicSetting:
                return true;
            default:
                return true;
        }
    }
}
