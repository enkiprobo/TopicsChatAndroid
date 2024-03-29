package com.example.enkiprobo.topicschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView mtvCreateTopic;
    private LinearLayout mlyCreateTopic;
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
    private String tempTopicName = "";

    public GroupsTopic getGroupTopic(){
        return chatTopic;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        inten = getIntent();
        Log.d("terakhir", "coba disini 1");
        updateGroupInfo();
        Log.d("terakhir", "coba disini 2");
        mtbGroupChat = (Toolbar) findViewById(R.id.tb_chatGroup);
        mivChatTopic = (ImageView) findViewById(R.id.iv_chatTopics);
        metChatMessage = (EditText) findViewById(R.id.et_chatMessage);
        mbtSend = (Button) findViewById(R.id.bt_chatSend);
        mrvTopicList = (RecyclerView) findViewById(R.id.rv_topicList);
        mrvChatList = (RecyclerView) findViewById(R.id.rv_chatList);
        mlyCreateTopic = (LinearLayout) findViewById(R.id.ly_createTopic);
        mtvCreateTopic = (TextView) findViewById(R.id.tv_addNewTopic);

        Log.d("terakhir", "coba disini 3");
        mtbGroupChat.setBackgroundColor(getResources().getColor(R.color.mainPurple));
        setSupportActionBar(mtbGroupChat);
        Log.d("MENCARIERROR", "DIDIDIDI?");
        chatDetailList = ChatDetail.getGroupsChat(this, groupIDGroup, groupName);

        Log.d("terakhir", "coba disini 4");
        GroupChatAdapter adapterGC = new GroupChatAdapter(this, chatDetailList);
        mrvChatList.setAdapter(adapterGC);
        mrvChatList.setLayoutManager(new LinearLayoutManager(this));
        mrvChatList.scrollToPosition(chatDetailList.size()-1);

        Log.d("MENCARIERROR", groupIDGroup+"");
        groupsTopicList = GroupsTopic.getGroupTopic(groupIDGroup);
        Log.d("terakhir", groupsTopicList+"");
        GroupTopicAdapter adapterGA = new GroupTopicAdapter(this, groupsTopicList);
        mrvTopicList.setAdapter(adapterGA);
        mrvTopicList.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false));
        Log.d("terakhir", "coba disini 6");
        metChatMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String temp= editable.toString();
                if (temp.length()>0){
                    if (temp.charAt(0) == '#'){

                        if (temp.length()>1){
                            if (temp.charAt(1) != ' '){
                                mlyCreateTopic.setVisibility(View.VISIBLE);

                                int indexSpace = temp.indexOf(' ');
                                if (indexSpace >1){
                                    tempTopicName = temp.substring(1,indexSpace);
                                }else {
                                    tempTopicName = temp.substring(1, temp.length());
                                }

                                Log.d("BISAKAH", tempTopicName);
                            } else{
                                tempTopicName = "";
                            }
                        }else {
                            tempTopicName = "";
                        }
                    }
                    mtvCreateTopic.setText("add new topic "+tempTopicName);

                    if(tempTopicName.length()<1){
                        mlyCreateTopic.setVisibility(View.GONE);
                    }else {
                        for (GroupsTopic topic: groupsTopicList) {
                            if (topic.getTopicName().toLowerCase().equals(tempTopicName.toLowerCase())){
                                mlyCreateTopic.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });

        Log.d("terakhir", "coba disini 8");
        Log.d("MENCARIERROR", "DISINI?");
        updateGroupInfo();
        updateTitle();
        WebsocketUtilTC.setChatLiveContext(this);
        Log.d("terakhir", "coba disini 9");
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("terakhir", "coba disini");
//        GroupChatAdapter adapterGC = (GroupChatAdapter) mrvChatList.getAdapter();
//        adapterGC.setChatDetailList(ChatDetail.getGroupsChat(this, groupIDGroup, groupName));
//
//        updateGroupInfo();
//        updateTitle();
//        WebsocketUtilTC.setChatLiveContext(this);
//    }

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
        Log.d("terakhir", "ini");
        for (GroupsTopic topic: groupsTopicList){
            Log.d("terakhir", "coba disini hmmm");
            if (topic.getTopicName().equals("All")){
                Log.d("terakhir", "coba disini nyampeee");
                chatTopic = topic;
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AKTIVITAS", "ADA KESINI GA?");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("AKTIVITAS", "hancur");
    }

    public void updateTitle(){

        if (chatTopic!= null){
            String groupNameShow = groupName.length()>17 ? groupName.substring(0,14)+"..." : String.format("%1$-" + 17 + "s", groupName);
            Log.d("terakhir", "coba disini sebelum 9");
            String chatTopicShow = chatTopic.getTopicName().length()>7 ? chatTopic.getTopicName().substring(0,3)+"..." : String.format("%1$-" + 7 + "s", chatTopic.getTopicName());
            ActionBar tb = this.getSupportActionBar();
            tb.setTitle(groupNameShow+" | "+chatTopicShow);

            GroupChatAdapter adapterGC =(GroupChatAdapter) mrvChatList.getAdapter();
            chatDetailList = ChatDetail.getGroupsChatFromTopic(this, chatTopic.getIdTopic());
            adapterGC.setChatDetailList(chatDetailList);
        }

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

    public void buatTopicBaru(View view) {
        if (tempTopicName.length()>0){
            new NetworkUtilTC().createTopic(this, tempTopicName, groupIDGroup);
            mlyCreateTopic.setVisibility(View.GONE);
            metChatMessage.setText("");
            
        }
    }
}
