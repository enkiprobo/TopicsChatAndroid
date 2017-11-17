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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import topicschat.adapters.GroupChatAdapter;
import topicschat.adapters.GroupTopicAdapter;
import topicschat.adapters.UserGroupAdapter;
import topicschat.helper.TPConstant;
import topicschat.networkutil.NetworkUtilTC;
import topicschat.networkutil.WebsocketUtilTC;
import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mtbGroupChat;
    private ImageView mivChatTopic;
    private EditText metChatMessage;
    private Button mbtSend;
    private RecyclerView mrvChatList;
    private RecyclerView mrvTopicList;
    private List<ChatDetail> chatDetailList;
    private List<GroupsTopic> groupsTopicList;
    private Intent inten;

    private int groupIDGroup;
    private int groupIDGM;
    private String groupName;
    private String groupImage;

    private GroupsTopic chatTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        inten = getIntent();
        updateGroupInfo();

        mtbGroupChat = (Toolbar) findViewById(R.id.tb_chatGroup);
        mivChatTopic = (ImageView) findViewById(R.id.iv_chatTopics);
        metChatMessage = (EditText) findViewById(R.id.et_chatMessage);
        mbtSend = (Button) findViewById(R.id.bt_chatSend);
        mrvTopicList = (RecyclerView) findViewById(R.id.rv_topicList);
        mrvChatList = (RecyclerView) findViewById(R.id.rv_chatList);

        setSupportActionBar(mtbGroupChat);
        Log.d("MENCARIERROR", "DIDIDIDI?");
        chatDetailList = ChatDetail.getGroupsChat(this, groupIDGroup);

        GroupChatAdapter adapterGC = new GroupChatAdapter(this, chatDetailList);
        mrvChatList.setAdapter(adapterGC);
        mrvChatList.setLayoutManager(new LinearLayoutManager(this));
        mrvChatList.scrollToPosition(chatDetailList.size()-1);

        Log.d("MENCARIERROR", "DISINDANG?");
        groupsTopicList = GroupsTopic.getGroupTopic(groupIDGroup);

        GroupTopicAdapter adapterGA = new GroupTopicAdapter(this, groupsTopicList);
        mrvTopicList.setAdapter(adapterGA);
        mrvTopicList.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false));

        Log.d("MENCARIERROR", "DISINI?");
        updateTitle();
        WebsocketUtilTC.setChatLiveContext(this);
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

    @Override
    protected void onResume() {
        super.onResume();

        GroupChatAdapter adapterGC = (GroupChatAdapter) mrvChatList.getAdapter();
        adapterGC.setChatDetailList(ChatDetail.getGroupsChat(this, groupIDGroup));

        updateGroupInfo();
        updateTitle();
        WebsocketUtilTC.setChatLiveContext(this);
    }

    public void setChatTopic(GroupsTopic chat){
        this.chatTopic = chat;
    }
    public void updateGroupInfo(){
        this.groupIDGM = inten.getIntExtra(TPConstant.EXTRA_IDGM, -1);
        this.groupIDGroup = inten.getIntExtra(TPConstant.EXTRA_IDGROUP, -1);
        this.groupImage = inten.getStringExtra(TPConstant.EXTRA_GROUPPHOTO);
        this.groupName = inten.getStringExtra(TPConstant.EXTRA_GROUPNAME);
        Log.d("MENCARIERROR", "ADAKAH?");
        groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", groupIDGroup+"");
        for (GroupsTopic topic: groupsTopicList){
            if (topic.getTopicName().equals("All")){
                chatTopic = topic;
                break;
            }
        }

    }
    public void updateTitle(){
        String groupNameShow = groupName.length()>17 ? groupName.substring(0,14)+"..." : String.format("%1$-" + 17 + "s", groupName);
        String chatTopicShow = chatTopic.getTopicName().length()>7 ? chatTopic.getTopicName().substring(0,3)+"..." : String.format("%1$-" + 7 + "s", chatTopic.getTopicName());
        mtbGroupChat.setTitle(groupNameShow+" - "+chatTopicShow);

        GroupChatAdapter adapterGC =(GroupChatAdapter) mrvChatList.getAdapter();
        chatDetailList = ChatDetail.getGroupsChatFromTopic(this, chatTopic.getIdTopic());
        adapterGC.setChatDetailList(chatDetailList);
    }
    public void kirimPesan(View view) {
        String message = metChatMessage.getText().toString();
        if (message.length()>0){
            new NetworkUtilTC().createChat(this, chatTopic.getIdTopic(), groupIDGM, message);
            metChatMessage.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WebsocketUtilTC.setContextToMain();
    }

    public void bukaTopic(View view) {
        mrvTopicList.setVisibility(mrvTopicList.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
    }
}
