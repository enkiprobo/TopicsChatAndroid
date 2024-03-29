package topicschat.networkutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.enkiprobo.topicschat.GroupChatActivity;
import com.example.enkiprobo.topicschat.R;
import com.example.enkiprobo.topicschat.RegistrationSuccessActivity;
import com.example.enkiprobo.topicschat.UserMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import topicschat.adapters.GroupChatAdapter;
import topicschat.adapters.GroupTopicAdapter;
import topicschat.adapters.UserGroupAdapter;
import topicschat.helper.DRYMethod;
import topicschat.helper.TPConstant;
import topicschat.sqlitedatamodel.ChatDetail;
import topicschat.sqlitedatamodel.GroupsTopic;
import topicschat.sqlitedatamodel.Mute;
import topicschat.sqlitedatamodel.UsersGroup;

/**
 * Created by enkiprobo on 11/14/2017.
 */

public class NetworkUtilTC {

//    private final String URL_BASE = "https://192.168.1.5/";
    private final String URL_BASE = "https://topicschatapi.herokuapp.com/";
    public static final String PREFERENCED_NAME = "com.example.enkiprobo.topicschat";

    private final String PARAM_USERNAME = "username";
    private final String PARAM_PASSWORD = "password";
    private final String PARAM_FULLNAME = "fullname";
    private final String PARAM_PROFILEIMAGE = "profileimage";
    private final String PARAM_BIRTHDATE = "birthdate";
    private final String PARAM_GROUPNAME = "groupname";
    private final String PARAM_GROUPIMAGE = "groupimage";
    private final String PARAM_IDTOPIC = "idtopic";
    private final String PARAM_IDGM = "idgm";
    private final String PARAM_MESSAGE = "message";
    private final String PARAM_IDGCD = "idgcd";
    private final String PARAM_PIN = "pin";
    private final String PARAM_IDGROUP = "idgroup";
    private final String PARAM_TOPICNAME = "topicname";

    private OkHttpClient client = new OkHttpClient();

    public void createUser(final Context context, String username, String password, String fullname, String profileimage, String birthdate) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_USERNAME, username)
                .addFormDataPart(PARAM_PASSWORD, password)
                .addFormDataPart(PARAM_FULLNAME, fullname)
                .addFormDataPart(PARAM_PROFILEIMAGE, profileimage)
                .addFormDataPart(PARAM_BIRTHDATE, birthdate)
                .build();

        Request request = new Request.Builder()
                .url(URL_BASE + "createuser")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessage);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_register);

                        tv.setVisibility(View.VISIBLE);
                        DRYMethod.buttonToRiple(bt);
                        tv.setText("please try again later");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String lompatan = "";
                try {
                    JSONObject hasil = new JSONObject(responseData);
                    lompatan = hasil.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String status = lompatan;

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessage);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_register);
                        if (!status.equals("OK")) {
                            tv.setText(status);
                            tv.setVisibility(View.VISIBLE);

                            DRYMethod.buttonToRiple(bt);
                        } else {
                            Intent inten = new Intent(context, RegistrationSuccessActivity.class);

                            context.startActivity(inten);
                            ((Activity) context).finish();
                        }
                    }
                });
            }
        });
    }

    public void Login(final Context context, String username, String password){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_USERNAME, username)
                .addFormDataPart(PARAM_PASSWORD, password)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "login")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorLogin);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_login);
                        
                        tv.setVisibility(View.VISIBLE);
                        tv.setText("please try again later");
                        DRYMethod.buttonToRiple(bt);
                        Log.d("MAJU", "network adapter gpp kok");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorLogin);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_login);
                        try {
                            JSONObject responseJson = new JSONObject(responseString);
                            String status = responseJson.getString("status");
                            if (!status.equals("OK")){
                                tv.setVisibility(View.VISIBLE);
                                tv.setText(status);
                                DRYMethod.buttonToRiple(bt);
                                Log.d("MENCOBALOGIN", "gak ok");
                            }else{
                                Log.d("MENCOBALOGIN", "berhasil mendapatkan status ok");
                                JSONObject userJson = responseJson.getJSONObject("user");
                                String username = userJson.getString("username");
                                String fullname = userJson.getString("full_name");
                                String profileImage = userJson.getString("profile_image");
                                String birthDate = userJson.getString("birth_date");

                                SharedPreferences mPreference = context.getSharedPreferences(PREFERENCED_NAME, context.MODE_PRIVATE);
                                SharedPreferences.Editor pEditor = mPreference.edit();
                                pEditor.putString("username", username);
                                pEditor.putString("fullname", fullname);
                                pEditor.putString("profileimage", profileImage);
                                pEditor.putString("birthdate", birthDate);
                                pEditor.apply();

                                if (WebsocketUtilTC.wss == null){
                                    WebsocketUtilTC.initWebsocket(context);
                                }
                                Intent inten = new Intent(context, UserMainActivity.class);
                                context.startActivity(inten);
                                ((Activity) context).finish();
                            }
                        } catch (JSONException e) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("username or password not exist");
                            DRYMethod.buttonToRiple(bt);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void getUserGroup(final Context context, String username){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_USERNAME, username)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "getusergroup")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_infoMain);

                        tv.setTextColor(context.getResources().getColor(R.color.redError));
                        tv.setText("Error while getting \nuser's group information");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_infoMain);
                        RecyclerView rv = (RecyclerView) ((Activity) context).findViewById(R.id.rv_userGroup);

                        try {
                            JSONObject responseJSON = new JSONObject(response.body().string());
                            String status = responseJSON.getString("status");
                            if (!status.equals("OK")){
                                tv.setText(context.getResources().getString(R.string.no_group));
                            } else {
                                JSONArray groupListJson = responseJSON.getJSONArray("group_list");
                                if (groupListJson.length() >0 ){
                                    for (int i = 0; i < groupListJson.length(); i++) {
                                        JSONObject groupJson = groupListJson.getJSONObject(i);

                                        int idGroup = groupJson.getInt("id_group");
                                        String groupName = groupJson.getString("group_name");
                                        String groupImage = groupJson.getString("group_image");
                                        int idGm = groupJson.getInt("id_gm");

                                        UsersGroup group = new UsersGroup(idGroup,idGm,groupName,groupImage,"","00:00",0);
                                        group.save();
                                        getGroupTopic(context, idGroup);
                                    }
                                    UserGroupAdapter adapter = (UserGroupAdapter) rv.getAdapter();

                                    adapter.setUsersGroupList(UsersGroup.listAll(UsersGroup.class));
                                    adapter.notifyDataSetChanged();

                                    tv.setVisibility(View.GONE);
                                    rv.setVisibility(View.VISIBLE);
                                }else{
                                    tv.setText(context.getResources().getString(R.string.no_group));
                                }

                            }
                        } catch (JSONException e) {
                            tv.setText(context.getResources().getString(R.string.no_group));
                            e.printStackTrace();
                        } catch (IOException e) {
                            tv.setText(context.getResources().getString(R.string.no_group));
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    public void createGroup(final Context context, String groupName, String groupImage, String username ){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_GROUPNAME, groupName)
                .addFormDataPart(PARAM_GROUPIMAGE, groupImage)
                .addFormDataPart(PARAM_USERNAME, username)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "creategroup")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessageCreateGroup);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_createGroup);

                        tv.setText("please try again later");
                        DRYMethod.buttonToRiple(bt);
                    }
                });
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessageCreateGroup);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_createGroup);

                        try {
                            JSONObject responseJSON = new JSONObject(response.body().string());
                            String status = responseJSON.getString("status");
                            if (!status.equals("OK")){
                                tv.setText("error while getting group data");
                                DRYMethod.buttonToRiple(bt);
                            } else {
                                JSONArray groupListJson = responseJSON.getJSONArray("group_list");
                                for (int i = 0; i < groupListJson.length(); i++) {
                                    JSONObject groupJson = groupListJson.getJSONObject(i);

                                    int idGroup = groupJson.getInt("id_group");
                                    String groupName = groupJson.getString("group_name");
                                    String groupImage = groupJson.getString("group_image");
                                    int idGm = groupJson.getInt("id_gm");

                                    UsersGroup group = new UsersGroup(idGroup,idGm,groupName,groupImage,"","00:00",0);
                                    group.save();
                                    Log.d("Kesini", "hoo");
                                }

                                Intent inten = new Intent(context, UserMainActivity.class);
                                context.startActivity(inten);
                                ((Activity) context).finish();
                            }
                        } catch (JSONException e) {
                            tv.setText("error while getting group data");
                            DRYMethod.buttonToRiple(bt);
                            e.printStackTrace();
                        } catch (IOException e) {
                            tv.setText("error while getting group data");
                            DRYMethod.buttonToRiple(bt);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void insertMember(final Context context, int idGroup, String username) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_IDGROUP, idGroup+"")
                .addFormDataPart(PARAM_USERNAME, username)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "insertmember")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessageInviteUser);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_inviteUser);

                        tv.setText("please try again later");
                        bt.setEnabled(true);
                        bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                    }
                });
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) ((Activity) context).findViewById(R.id.tv_errorMessageInviteUser);
                        Button bt = (Button) ((Activity) context).findViewById(R.id.bt_inviteUser);

                        try {
                            JSONObject responseJson = new JSONObject(response.body().string());
                            String status = responseJson.getString("status");

                            if (!status.equals("OK")){
                                tv.setText("user's doesn't exist");
                                bt.setEnabled(true);
                                bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                            }else {
                                Intent inten = new Intent(context, GroupChatActivity.class);
                                context.startActivity(inten);
                                ((Activity) context).finish();
                            }
                        } catch (JSONException e) {
                            tv.setText("user does't not exist");
                            bt.setEnabled(true);
                            bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                            e.printStackTrace();
                        } catch (IOException e){
                            tv.setText("error while getting user data");
                            bt.setEnabled(true);
                            bt.setBackgroundColor(context.getResources().getColor(R.color.mainPurple));
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
    public void getChatGroupAll(final Context context, final int idGroup){
        Log.d("CHATTINGBABARENGAN", "baru manggil chat group");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_IDGROUP, idGroup+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "getchatgroupall")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d("CHATTINGBABARENGAN", "sama ini duluan mana?");
                    JSONObject responseJson = new JSONObject(response.body().string());
                    JSONArray chatListJson = responseJson.getJSONArray("chat_list");
                    for (int i = 0; i < chatListJson.length(); i++) {
                        JSONObject chatJson = chatListJson.getJSONObject(i);

                        int idGCD = chatJson.getInt("id_gcd");
                        String message = chatJson.getString("chat_message");
                        int idTopic = chatJson.getInt("id_topic");
                        boolean pin = chatJson.getBoolean("pin");
                        String createdTime = chatJson.getString("created_time");
                        String username = chatJson.getString("username");
                        int idGM = chatJson.getInt("id_gm");
                        JSONObject user = chatJson.getJSONObject("user");
                        String userUsername = user.getString("username");
                        String userUserFullname = user.getString("full_name");
                        String userUserBirth = user.getString("birth_date");
                        String userUserImage = user.getString("profile_image");

//                        ChatDetail chat = new ChatDetail(idGCD,message,idTopic,pin,createdTime);
                        ChatDetail chat = new ChatDetail(idGCD,message,idTopic,pin,createdTime,userUsername,userUserImage,userUserFullname,userUserBirth);
                        chat.save();
                    }

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            RecyclerView rv = (RecyclerView) ((Activity) context).findViewById(R.id.rv_chatList);
                            if (rv != null){
                                GroupChatAdapter gca = (GroupChatAdapter) rv.getAdapter();
                                gca.update(idGroup);
                                gca.notifyDataSetChanged();
                            }

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getGroupTopic(final Context context, final int idGroup){
        Log.d("CHATTINGBABARENGAN", "dipanggil nih");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PARAM_IDGROUP, idGroup+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "getgrouptopic")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // error while getting topic
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d("CHATTINGBABARENGAN", "SUDAH DISINI");
                    JSONObject responseJson = new JSONObject(response.body().string());
                    JSONArray topicListJson = responseJson.getJSONArray("topic_list");

                    for (int i = 0; i < topicListJson.length(); i++) {
                        JSONObject topicJson = topicListJson.getJSONObject(i);

                        int idTopic = topicJson.getInt("id_topic");
                        String topicName = topicJson.getString("topic_name");
                        int idGroup = topicJson.getInt("id_group");

                        GroupsTopic topics = new GroupsTopic(idTopic,topicName,idGroup);
                        topics.save();
                    }

                    if (context instanceof GroupChatActivity){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GroupsTopic topic = GroupsTopic.find(GroupsTopic.class, "id_group = ? AND topic_name = ?", idGroup+"", "All").get(0);
                                UsersGroup group = UsersGroup.find(UsersGroup.class, "id_group = ?", topic.getIdGroup()+"").get(0);

                                android.support.v7.app.ActionBar tb = ((AppCompatActivity)context).getSupportActionBar();

                                String groupNameShow = group.getGroupName().length()>17 ? group.getGroupName().substring(0,14)+"..." : String.format("%1$-" + 17 + "s", group.getGroupName());
                                Log.d("terakhir", "coba disini sebelum 9");
                                String chatTopicShow = topic.getTopicName().length()>7 ? topic.getTopicName().substring(0,3)+"..." : String.format("%1$-" + 7 + "s", topic.getTopicName());
                                tb.setTitle(groupNameShow+" | "+chatTopicShow);
                            }
                        });
                    }


                    getChatGroupAll(context, idGroup);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createChat(final Context context, int idTopic, int idGM, String message){
        Log.d("CHATTINGBABARENGAN", "dipanggil nih");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_IDGM, idGM+"")
                .addFormDataPart(TPConstant.PARAM_IDTOPIC, idTopic+"")
                .addFormDataPart(TPConstant.PARAM_MESSAGE, message)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "createchat")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

            }
        });
    }

    public void createTopic(Context context, String topicName, int idGroup){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_TOPICNAME, topicName)
                .addFormDataPart(TPConstant.PARAM_IDGROUP, idGroup+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "createtopic")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void muteChat(final Context context, String username, final int idTopic){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_USERNAME, username)
                .addFormDataPart(TPConstant.PARAM_IDTOPIC, idTopic+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "createmute")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJSON = new JSONObject(response.body().string());

                    String status = responseJSON.getString("status");
                    if (status.equals("OK")){
                        Mute mute = new Mute(idTopic);
                        mute.save();

                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView rv = ((Activity) context).findViewById(R.id.rv_topicList);

                                GroupTopicAdapter adapter = (GroupTopicAdapter) rv.getAdapter();
                                GroupsTopic topic = GroupsTopic.find(GroupsTopic.class, "id_topic = ?", idTopic+"").get(0);
                                List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", topic.getIdGroup()+"");
                                adapter.updateTopicList(groupsTopicList);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void unmuteChat(final Context context, String username, final int idTopic){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_USERNAME, username)
                .addFormDataPart(TPConstant.PARAM_IDTOPIC, idTopic+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "deletemute")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJSON = new JSONObject(response.body().string());

                    String status = responseJSON.getString("status");
                    if (status.equals("OK")){
                        Mute mute = Mute.find(Mute.class, "id_topic = ?", idTopic+"").get(0);
                        Mute.delete(mute);

                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView rv = ((Activity) context).findViewById(R.id.rv_topicList);

                                GroupTopicAdapter adapter = (GroupTopicAdapter) rv.getAdapter();
                                GroupsTopic topic = GroupsTopic.find(GroupsTopic.class, "id_topic = ?", idTopic+"").get(0);
                                List<GroupsTopic> groupsTopicList = GroupsTopic.find(GroupsTopic.class, "id_group = ?", topic.getIdGroup()+"");
                                adapter.updateTopicList(groupsTopicList);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllMute(String username){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_USERNAME, username)
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "getmutelist")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    String status = responseJson.getString("status");
                    if (status.equals("OK")){
                        JSONArray topicMuteListJson = responseJson.getJSONArray("topic_id_list_mute");
                        for (int i =0; i< topicMuteListJson.length();i++){
                            int idTopic = topicMuteListJson.getInt(i);
                            Mute mute = new Mute(idTopic);
                            mute.save();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void pinChat (final Context context, boolean pin, final int idGCD){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TPConstant.PARAM_PIN, pin+"")
                .addFormDataPart(TPConstant.PARAM_IDGCD, idGCD+"")
                .build();

        final Request request = new Request.Builder()
                .url(URL_BASE + "pinchat")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());

                    String status = responseJson.getString("status");
                    if (status.equals("OK")){
                        ChatDetail chatDetail = ChatDetail.find(ChatDetail.class, "id_chat = ?", idGCD+"").get(0);
                        chatDetail.setPinned(true);
                        chatDetail.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}