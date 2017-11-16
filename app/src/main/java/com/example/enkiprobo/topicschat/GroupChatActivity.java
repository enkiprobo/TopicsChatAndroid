package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import topicschat.adapters.GroupChatAdapter;
import topicschat.adapters.UserGroupAdapter;
import topicschat.networkutil.NetworkUtilTC;
import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mtbGroupChat;
    private ImageView mivChatTopic;
    private EditText metChatMessage;
    private Button mbtSend;
    private RecyclerView mrvChatList;
    private List<ChatDetail> chatDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        mtbGroupChat = (Toolbar) findViewById(R.id.tb_chatGroup);
        mivChatTopic = (ImageView) findViewById(R.id.iv_chatTopics);
        metChatMessage = (EditText) findViewById(R.id.et_chatMessage);
        mbtSend = (Button) findViewById(R.id.bt_chatSend);

        setSupportActionBar(mtbGroupChat);

        Intent inten = getIntent();
        int idGroup = inten.getIntExtra(UserGroupAdapter.EXTRA_IDGROUP, -1);
        Log.d("CHATTINGBABARENGAN", "id group = "+idGroup);
        List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", idGroup+"");
        String whereClause = "";
        if (groupsTopicList != null){
            if (groupsTopicList.size()>0){
                Log.d("CHATTINGBABARENGAN", "lebih dari nol, "+groupsTopicList.size());
                for (int i = 0; i < groupsTopicList.size(); i++) {
                    whereClause += (i==0)? "id_topic = "+groupsTopicList.get(i).getIdTopic(): " OR id_topic = "+groupsTopicList.get(i).getIdTopic();
                }
            }else {
                Log.d("CHATTINGBABARENGAN", "jalankan network");
                NetworkUtilTC networkUtilTC = new NetworkUtilTC();
                networkUtilTC.getGroupTopic(this, idGroup);
                networkUtilTC.getChatGroupAll(this, idGroup);
            }

            Log.d("CHATTINGBABARENGAN", whereClause);
            chatDetailList = ChatDetail.find(ChatDetail.class, whereClause, null);
        } else {
            NetworkUtilTC networkUtilTC = new NetworkUtilTC();
            networkUtilTC.getGroupTopic(this, idGroup);
            networkUtilTC.getChatGroupAll(this, idGroup);
        }

        GroupChatAdapter adapter = new GroupChatAdapter(this, chatDetailList);
        mrvChatList = (RecyclerView) findViewById(R.id.rv_chatList);
        mrvChatList.setAdapter(adapter);
        mrvChatList.setLayoutManager(new LinearLayoutManager(this));
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
